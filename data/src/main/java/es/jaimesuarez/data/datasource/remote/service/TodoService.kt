package es.jaimesuarez.data.datasource.remote.service

import es.jaimesuarez.data.model.TodoRemote
import retrofit2.Response
import retrofit2.http.*


interface TodoService {

    @GET("/todos")
    suspend fun getTodosByUser(
        @Query("userId") userId: Int
    ): Response<List<TodoRemote>>

    @POST("/todos")
    suspend fun postTodo(
        @Body todo: TodoRemote
    ): Response<TodoRemote>

    @DELETE("/todos/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId") todoId: Int
    ): Response<TodoRemote>

}
