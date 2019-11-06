package es.jaimesuarez.rindustest.post.model

import android.os.Parcelable
import es.jaimesuarez.domain.model.Post
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostDisplay(
    val id: Int,
    val title: String,
    val body: String
): Parcelable {

    constructor(post: Post) : this(id = post.id, title = post.title, body = post.body)
}
