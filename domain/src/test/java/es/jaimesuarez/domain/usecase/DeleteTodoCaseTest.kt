package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeleteTodoCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var deleteTodoCase: DeleteTodoCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Delete an existing Todo in the repository`() {
        // Given
        val todoId = 1
        coEvery { repository.deleteTodo(todoId) } returns mockk()

        // When
        runBlocking {
            deleteTodoCase.delete(todoId)
        }

        // Then
        coVerify { repository.deleteTodo(todoId) }
    }
}