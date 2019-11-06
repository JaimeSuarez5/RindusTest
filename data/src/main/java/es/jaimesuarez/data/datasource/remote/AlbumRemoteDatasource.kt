package es.jaimesuarez.data.datasource.remote

import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.data.datasource.remote.service.AlbumService
import es.jaimesuarez.data.extensions.perform
import es.jaimesuarez.data.model.AlbumRemote
import es.jaimesuarez.data.model.PhotoRemote

class AlbumRemoteDatasource(
    private val albumService: AlbumService
) {

    suspend fun getAlbumsByUser(userId: Int): Result<List<AlbumRemote>> {
        return albumService.getAlbumsByUser(userId).perform()
    }

    suspend fun getPhotosByAlbum(albumId: Int): Result<List<PhotoRemote>> {
        return albumService.getPhotosByAlbum(albumId).perform()
    }
}