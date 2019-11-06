package es.jaimesuarez.rindustest.post.model

import es.jaimesuarez.domain.model.Comment


data class CommentDisplay(
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) {
    constructor(comment: Comment) : this(
        id = comment.id,
        name = comment.name,
        email = comment.email,
        body = comment.body
    )
}
