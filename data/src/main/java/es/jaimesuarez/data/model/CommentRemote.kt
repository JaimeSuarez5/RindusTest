package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.Comment

data class CommentRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
) {
    fun toDomain() = Comment(
        id = id,
        name = name,
        email = email,
        body = body
    )
}
