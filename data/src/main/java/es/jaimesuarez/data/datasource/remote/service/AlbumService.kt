package es.jaimesuarez.data.datasource.remote.service

import es.jaimesuarez.data.model.AlbumRemote
import es.jaimesuarez.data.model.PhotoRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbumsByUser(
        @Query("userId") userId: Int
    ): Response<List<AlbumRemote>>

    @GET("/albums/1/photos")
    suspend fun getPhotosByAlbum(
        @Query("albumId") albumId: Int
    ): Response<List<PhotoRemote>>
}
