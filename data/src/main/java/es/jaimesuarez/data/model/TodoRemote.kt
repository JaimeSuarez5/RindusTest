package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.Todo

data class TodoRemote(
    @SerializedName("id") val id: Int?,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("completed") val completed: Boolean
) {

    constructor(todo: Todo) : this(todo.id, todo.userId, todo.title, todo.completed)

    fun toDomain() = Todo(
        id = id,
        userId = userId,
        title = title,
        completed = completed
    )
}
