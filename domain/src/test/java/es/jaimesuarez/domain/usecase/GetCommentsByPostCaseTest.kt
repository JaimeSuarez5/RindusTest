package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCommentsByPostCaseTest {

    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var getCommentsByPostCase: GetCommentsByPostCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @After
    fun after() = unmockkAll()

    @Test
    fun `Get comments by post id`() {
        // Given
        val postId = 2
        coEvery { repository.getCommentsByPost(postId) } returns mockk()

        // When
        runBlocking {
            getCommentsByPostCase.get(postId)
        }

        // Then
        coVerify { repository.getCommentsByPost(postId) }
    }
}