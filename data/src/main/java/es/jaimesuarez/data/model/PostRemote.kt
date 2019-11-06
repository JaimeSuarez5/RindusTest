package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.Post

data class PostRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
) {
    fun toDomain() = Post(
        id = id,
        title = title,
        body = body
    )
}
