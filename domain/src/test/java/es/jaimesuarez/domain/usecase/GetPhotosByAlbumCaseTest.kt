package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetPhotosByAlbumCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var getPhotosByAlbumCase: GetPhotosByAlbumCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get photos by album id`() {
        // Given
        val albumId = 3
        coEvery { repository.getPhotosByAlbum(albumId) } returns mockk()

        // When
        runBlocking {
            getPhotosByAlbumCase.get(albumId)
        }

        // Then
        coVerify { repository.getPhotosByAlbum(albumId) }
    }
}