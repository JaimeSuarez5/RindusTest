package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPhotosByAlbumCase(private val userRepository: UserRepository) {

    suspend fun get(albumId: Int) = withContext(Dispatchers.IO) {
        userRepository.getPhotosByAlbum(albumId)
    }
}