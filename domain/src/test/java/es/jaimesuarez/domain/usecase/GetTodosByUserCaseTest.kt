package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetTodosByUserCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var getTodosByUserCase: GetTodosByUserCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get todos by user id`() {
        // Given
        val userId = 1
        coEvery { repository.getTodosByUser(userId) } returns mockk()

        // When
        runBlocking {
            getTodosByUserCase.get(userId)
        }

        // Then
        coVerify { repository.getTodosByUser(userId) }
    }
}