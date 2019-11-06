package es.jaimesuarez.rindustest.album.model

import es.jaimesuarez.domain.model.Photo

data class PhotoDisplay(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val url: String
) {

    constructor(photo: Photo) : this(
        id = photo.id,
        title = photo.title,
        thumbnail = photo.thumbnail,
        url = photo.url
    )
}