package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetUserCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var getUserCase: GetUserCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get user list from the repository`() {
        // Given
        coEvery { repository.getUser() } returns mockk()

        // When
        runBlocking {
            getUserCase.get()
        }

        // Then
        coVerify { repository.getUser() }
    }
}