package es.jaimesuarez.domain.repository

import es.jaimesuarez.domain.model.*
import es.jaimesuarez.domain.util.Result

interface UserRepository {

    suspend fun getUser(): Result<List<User>>

    // Posts

    suspend fun getPostsByUser(userId: Int): Result<List<Post>>

    suspend fun getCommentsByPost(postId: Int): Result<List<Comment>>

    // Albums

    suspend fun getAlbumsByUser(userId: Int): Result<List<Album>>

    suspend fun getPhotosByAlbum(albumId: Int): Result<List<Photo>>

    // Todos

    suspend fun getTodosByUser(userId: Int): Result<List<Todo>>

    suspend fun createTodo(todo: Todo): Result<Todo>

    suspend fun deleteTodo(todoId: Int): Result<Boolean>
}
