package es.jaimesuarez.data.repository

import es.jaimesuarez.data.datasource.local.LocalCacheDatasource
import es.jaimesuarez.data.datasource.remote.AlbumRemoteDatasource
import es.jaimesuarez.data.datasource.remote.PostRemoteDatasource
import es.jaimesuarez.data.datasource.remote.TodoRemoteDatasource
import es.jaimesuarez.data.datasource.remote.UserRemoteDatasource
import es.jaimesuarez.data.model.*
import es.jaimesuarez.domain.model.Album
import es.jaimesuarez.domain.model.Photo
import es.jaimesuarez.domain.model.Post
import es.jaimesuarez.domain.model.Todo
import es.jaimesuarez.domain.util.Result
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    @MockK
    private lateinit var userRemoteDatasource: UserRemoteDatasource
    @MockK
    private lateinit var postRemoteDatasource: PostRemoteDatasource
    @MockK
    private lateinit var albumRemoteDatasource: AlbumRemoteDatasource
    @MockK
    private lateinit var todoRemoteDatasource: TodoRemoteDatasource
    @MockK
    private lateinit var albumsCache: LocalCacheDatasource<List<Album>>
    @MockK
    private lateinit var todosCache: LocalCacheDatasource<List<Todo>>
    @MockK
    private lateinit var postsCache: LocalCacheDatasource<List<Post>>
    @MockK
    private lateinit var photosCache: LocalCacheDatasource<List<Photo>>

    @InjectMockKs
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get an user`() {
        // Given
        val userRemote = mockk<UserRemote>()
        every { userRemote.toDomain() } returns mockk()
        coEvery { userRemoteDatasource.getUser() } returns Result.Success(listOf(userRemote, userRemote))

        // When
        runBlocking {
            repository.getUser()
        }

        // Then
        coVerify { userRemoteDatasource.getUser() }
        verify(atLeast = 2) { userRemote.toDomain() }
    }

    @Test
    fun `Get posts by user NOT cached`() {
        // Given
        val userId = 1
        val postRemote = mockk<PostRemote>()
        val result = Result.Success(listOf(postRemote, postRemote, postRemote))
        every { postRemote.toDomain() } returns mockk()
        every { postsCache.isCached(userId) } returns false
        every { postsCache.save(userId, any()) } just runs
        coEvery { postRemoteDatasource.getPostsByUser(userId) } returns result

        // When
        runBlocking {
            repository.getPostsByUser(userId)
        }

        // Then
        verify { postsCache.isCached(userId) }
        coVerify { postRemoteDatasource.getPostsByUser(userId) }
        verify(atLeast = 3) { postRemote.toDomain() }
        verify { postsCache.save(userId, any()) }
    }

    @Test
    fun `Get posts by user cached`() {
        // Given
        val userId = 1
        val post = mockk<Post>()
        every { postsCache.isCached(userId) } returns true
        every { postsCache.getCached(userId) } returns Result.Success(listOf(post))

        // When
        runBlocking {
            repository.getPostsByUser(userId)
        }

        // Then
        verify { postsCache.isCached(userId) }
        verify { postsCache.getCached(userId) }
    }

    @Test
    fun `Get comments by post`() {
        // Given
        val postId = 1
        val commentRemote = mockk<CommentRemote>()
        val result = Result.Success(listOf(commentRemote, commentRemote))

        every { commentRemote.toDomain() } returns mockk()
        coEvery { postRemoteDatasource.getCommentsByPost(postId) } returns result

        // When
        runBlocking {
            repository.getCommentsByPost(postId)
        }

        // Then
        coVerify { postRemoteDatasource.getCommentsByPost(postId) }
        verify(atLeast = 2) { commentRemote.toDomain() }
    }

    @Test
    fun `Get albums by user NOT cached`() {
        // Given
        val userId = 1
        val albumRemote = mockk<AlbumRemote>()
        val result = Result.Success(listOf(albumRemote, albumRemote, albumRemote))
        every { albumRemote.toDomain() } returns mockk()
        every { albumsCache.isCached(userId) } returns false
        every { albumsCache.save(userId, any()) } just runs
        coEvery { albumRemoteDatasource.getAlbumsByUser(userId) } returns result

        // When
        runBlocking {
            repository.getAlbumsByUser(userId)
        }

        // Then
        verify { albumsCache.isCached(userId) }
        coVerify { albumRemoteDatasource.getAlbumsByUser(userId) }
        verify(atLeast = 3) { albumRemote.toDomain() }
        verify { albumsCache.save(userId, any()) }
    }

    @Test
    fun `Get albums by user cached`() {
        // Given
        val userId = 1
        val album = mockk<Album>()
        every { albumsCache.isCached(userId) } returns true
        every { albumsCache.getCached(userId) } returns Result.Success(listOf(album))

        // When
        runBlocking {
            repository.getAlbumsByUser(userId)
        }

        // Then
        verify { albumsCache.isCached(userId) }
        verify { albumsCache.getCached(userId) }
    }

    @Test
    fun `Get photos by album NOT cached`() {
        // Given
        val albumId = 1
        val photoRemote = mockk<PhotoRemote>()
        val result = Result.Success(listOf(photoRemote, photoRemote))

        every { photoRemote.toDomain() } returns mockk()
        every { photosCache.isCached(albumId) } returns false
        every { photosCache.save(albumId, any()) } just runs
        coEvery { albumRemoteDatasource.getPhotosByAlbum(albumId) } returns result

        // When
        runBlocking {
            repository.getPhotosByAlbum(albumId)
        }

        // Then
        verify { photosCache.isCached(albumId) }
        coVerify { albumRemoteDatasource.getPhotosByAlbum(albumId) }
        verify(atLeast = 2) { photoRemote.toDomain() }
        verify { photosCache.save(albumId, any()) }
    }

    @Test
    fun `Get photos by album cached`() {
        // Given
        val albumId = 1
        val photo = mockk<Photo>()
        every { photosCache.isCached(albumId) } returns true
        every { photosCache.getCached(albumId) } returns Result.Success(listOf(photo))

        // When
        runBlocking {
            repository.getPhotosByAlbum(albumId)
        }

        // Then
        verify { photosCache.isCached(albumId) }
        verify { photosCache.getCached(albumId) }
    }

    @Test
    fun `Get todos by user NOT cached`() {
        // Given
        val userId = 1
        val todoRemote = mockk<TodoRemote>()
        val result = Result.Success(listOf(todoRemote, todoRemote))

        every { todoRemote.toDomain() } returns mockk()
        every { todosCache.isCached(userId) } returns false
        every { todosCache.save(userId, any()) } just runs
        coEvery { todoRemoteDatasource.getTodosByUser(userId) } returns result

        // When
        runBlocking {
            repository.getTodosByUser(userId)
        }

        // Then
        verify { todosCache.isCached(userId) }
        coVerify { todoRemoteDatasource.getTodosByUser(userId) }
        verify(atLeast = 2) { todoRemote.toDomain() }
        verify { todosCache.save(userId, any()) }
    }

    @Test
    fun `Get todos by user cached`() {
        // Given
        val userId = 1
        val todo = mockk<Todo>()
        every { todosCache.isCached(userId) } returns true
        every { todosCache.getCached(userId) } returns Result.Success(listOf(todo))

        // When
        runBlocking {
            repository.getTodosByUser(userId)
        }

        // Then
        verify { todosCache.isCached(userId) }
        verify { todosCache.getCached(userId) }
    }

    @Test
    fun `Post a Todo`() {
        // Given
        val todo = Todo(userId = 2, title = "Title", completed = false)
        val todoRemote = TodoRemote(todo)
        coEvery { todoRemoteDatasource.postTodo(todoRemote) } returns Result.Success(todoRemote)

        // When
        runBlocking {
            repository.createTodo(todo)
        }

        // Then
        coVerify { todoRemoteDatasource.postTodo(todoRemote) }
    }

}