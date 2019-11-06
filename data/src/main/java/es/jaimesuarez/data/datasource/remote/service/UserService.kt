package es.jaimesuarez.data.datasource.remote.service

import es.jaimesuarez.data.model.UserRemote
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserRemote>>

}
