package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.model.User
import es.jaimesuarez.domain.repository.UserRepository
import es.jaimesuarez.domain.util.Result
import kotlinx.coroutines.*

class GetUserCase(private val userRepository: UserRepository) {

    suspend fun get(): Result<List<User>> = withContext(Dispatchers.IO) {
        userRepository.getUser()
    }

}
