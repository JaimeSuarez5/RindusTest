package es.jaimesuarez.rindustest.todo.model

import android.os.Parcelable
import es.jaimesuarez.domain.model.Todo
import kotlinx.android.parcel.Parcelize

@Parcelize
class TodoDisplay(
    val id: Int?,
    val userId: Int,
    val title: String,
    val completed: Boolean
) : Parcelable {

    constructor(todo: Todo) : this(
        id = todo.id,
        userId = todo.userId,
        title = todo.title,
        completed = todo.completed
    )
}