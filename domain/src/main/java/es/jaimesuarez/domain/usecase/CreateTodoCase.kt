package es.jaimesuarez.domain.usecase

import es.jaimesuarez.domain.model.Todo
import es.jaimesuarez.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateTodoCase(private val userRepository: UserRepository) {

    suspend fun create(todo: Todo) = withContext(Dispatchers.IO) {
        userRepository.createTodo(todo)
    }
}
