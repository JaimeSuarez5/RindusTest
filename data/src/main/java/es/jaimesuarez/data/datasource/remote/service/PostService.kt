package es.jaimesuarez.data.datasource.remote.service

import es.jaimesuarez.data.model.CommentRemote
import es.jaimesuarez.data.model.PostRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("/posts")
    suspend fun getPostsByUser(
        @Query("userId") userId: Int
    ): Response<List<PostRemote>>

    @GET("/posts/1/comments")
    suspend fun getCommentsByPost(
        @Query("postId") postId: Int
    ): Response<List<CommentRemote>>
}
