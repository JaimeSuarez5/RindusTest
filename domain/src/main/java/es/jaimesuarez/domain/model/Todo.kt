package es.jaimesuarez.domain.model

data class Todo(
    val id: Int? = null,
    val userId: Int,
    val title: String,
    val completed: Boolean
)
