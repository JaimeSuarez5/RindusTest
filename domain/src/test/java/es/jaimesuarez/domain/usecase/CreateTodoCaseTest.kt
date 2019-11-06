package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.model.Todo
import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CreateTodoCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var createTodoCase: CreateTodoCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Post a new Todo to the repository`() {
        // Given
        val todo = Todo(userId = 1, title = "Todo", completed = false)
        coEvery { repository.createTodo(todo) } returns mockk()

        // When
        runBlocking {
            createTodoCase.create(todo)
        }

        // Then
        coVerify { repository.createTodo(todo) }
    }
}