package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.Photo

data class PhotoRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnail: String
) {

    fun toDomain() = Photo(
        id = id,
        title = title,
        url = url,
        thumbnail = thumbnail
    )
}
