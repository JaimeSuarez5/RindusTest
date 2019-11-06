package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTodoCase(private val userRepository: UserRepository) {

    suspend fun delete(todoId: Int) = withContext(Dispatchers.IO) {
        userRepository.deleteTodo(todoId)
    }
}