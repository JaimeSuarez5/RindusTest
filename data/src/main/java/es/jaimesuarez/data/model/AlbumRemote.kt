package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.Album

data class AlbumRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
) {
    fun toDomain() = Album(
        id = id,
        title = title
    )
}
