package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAlbumsByUserCase(private val userRepository: UserRepository) {

    suspend fun get(userId: Int) = withContext(Dispatchers.IO) {
        userRepository.getAlbumsByUser(userId)
    }
}