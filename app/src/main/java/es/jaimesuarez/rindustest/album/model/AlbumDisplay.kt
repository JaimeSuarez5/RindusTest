package es.jaimesuarez.rindustest.album.model

import android.os.Parcelable
import es.jaimesuarez.domain.model.Album
import kotlinx.android.parcel.Parcelize

@Parcelize
class AlbumDisplay(
    val id: Int,
    val title: String
) : Parcelable {

    constructor(album: Album) : this(id = album.id, title = album.title)
}
