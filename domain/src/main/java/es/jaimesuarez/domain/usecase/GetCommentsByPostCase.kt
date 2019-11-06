package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCommentsByPostCase(private val userRepository: UserRepository) {

    suspend fun get(postId: Int) = withContext(Dispatchers.IO) {
        userRepository.getCommentsByPost(postId)
    }
}