package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetAlbumsByUserCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var getAlbumsByUserCase: GetAlbumsByUserCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get albums by user id`() {
        // Given
        val userId = 2
        coEvery { repository.getAlbumsByUser(userId) } returns mockk()

        // When
        runBlocking {
            getAlbumsByUserCase.get(userId)
        }

        // Then
        coVerify { repository.getAlbumsByUser(userId) }
    }
}