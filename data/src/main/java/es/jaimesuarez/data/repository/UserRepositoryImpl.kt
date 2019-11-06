package es.jaimesuarez.data.repository

import es.jaimesuarez.data.datasource.local.LocalCacheDatasource
import es.jaimesuarez.data.datasource.remote.AlbumRemoteDatasource
import es.jaimesuarez.data.datasource.remote.PostRemoteDatasource
import es.jaimesuarez.data.datasource.remote.TodoRemoteDatasource
import es.jaimesuarez.data.datasource.remote.UserRemoteDatasource
import es.jaimesuarez.data.model.TodoRemote
import es.jaimesuarez.domain.model.*
import es.jaimesuarez.domain.repository.UserRepository
import es.jaimesuarez.domain.util.ErrorKind
import es.jaimesuarez.domain.util.Result
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepositoryImpl(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val postRemoteDatasource: PostRemoteDatasource,
    private val albumRemoteDatasource: AlbumRemoteDatasource,
    private val todoRemoteDatasource: TodoRemoteDatasource,
    private val albumsCache: LocalCacheDatasource<List<Album>>,
    private val todosCache: LocalCacheDatasource<List<Todo>>,
    private val postsCache: LocalCacheDatasource<List<Post>>,
    private val photosCache: LocalCacheDatasource<List<Photo>>
) : UserRepository {

    private suspend fun <T> executeSafely(function: suspend () -> Result<T>): Result<T> {
        return try {
            function()
        } catch (e: UnknownHostException) {
            Result.Failure(ErrorKind.ApiError(e))
        } catch (e: SocketTimeoutException) {
            Result.Failure(ErrorKind.UnknownError(e))
        }
    }

    // Users

    override suspend fun getUser(): Result<List<User>> = executeSafely {
        userRemoteDatasource.getUser().mapResult { list ->
            list.map { it.toDomain() }
        }
    }

    // Comments

    override suspend fun getPostsByUser(userId: Int): Result<List<Post>> = executeSafely {
        if (postsCache.isCached(userId)) {
            postsCache.getCached(userId)
        } else {
            postRemoteDatasource.getPostsByUser(userId).mapResult { list ->
                list.map { it.toDomain() }
            }.also { if (it is Result.Success) postsCache.save(userId, it) }
        }
    }

    override suspend fun getCommentsByPost(postId: Int): Result<List<Comment>> = executeSafely {
        postRemoteDatasource.getCommentsByPost(postId).mapResult { list ->
            list.map { it.toDomain() }
        }
    }

    // Albums

    override suspend fun getAlbumsByUser(userId: Int): Result<List<Album>> = executeSafely {
        if (albumsCache.isCached(userId)) {
            albumsCache.getCached(userId)
        } else {
            albumRemoteDatasource.getAlbumsByUser(userId).mapResult { list ->
                list.map { it.toDomain() }
            }.also { if (it is Result.Success) albumsCache.save(userId, it) }
        }
    }

    override suspend fun getPhotosByAlbum(albumId: Int): Result<List<Photo>> = executeSafely {
        if (photosCache.isCached(albumId)) {
            photosCache.getCached(albumId)
        } else {
            albumRemoteDatasource.getPhotosByAlbum(albumId).mapResult { list ->
                list.map { it.toDomain() }
            }.also { photosCache.save(albumId, it) }
        }
    }

    // Todos

    override suspend fun getTodosByUser(userId: Int): Result<List<Todo>> = executeSafely {
        if (todosCache.isCached(userId)) {
            todosCache.getCached(userId)
        } else {
            todoRemoteDatasource.getTodosByUser(userId).mapResult { list ->
                list.map { it.toDomain() }
            }.also { if (it is Result.Success) todosCache.save(userId, it) }
        }
    }

    override suspend fun createTodo(todo: Todo): Result<Todo> {
        return todoRemoteDatasource.postTodo(TodoRemote(todo)).mapResult { it.toDomain() }
    }

    override suspend fun deleteTodo(todoId: Int): Result<Boolean> {
        return todoRemoteDatasource.deleteTodo(todoId).mapResult { true }
    }
}
