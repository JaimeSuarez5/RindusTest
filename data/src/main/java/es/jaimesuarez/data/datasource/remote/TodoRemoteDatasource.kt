package es.jaimesuarez.data.datasource.remote

import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.data.datasource.remote.service.TodoService
import es.jaimesuarez.data.extensions.perform
import es.jaimesuarez.data.model.TodoRemote

class TodoRemoteDatasource(
    private val todoService: TodoService
) {

    suspend fun getTodosByUser(userId: Int): Result<List<TodoRemote>> {
        return todoService.getTodosByUser(userId).perform()
    }

    suspend fun postTodo(todo: TodoRemote): Result<TodoRemote> {
        return todoService.postTodo(todo).perform()
    }

    suspend fun deleteTodo(todoId: Int): Result<TodoRemote> {
        return todoService.deleteTodo(todoId).perform()
    }
}
