package es.jaimesuarez.data.datasource.remote

import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.data.datasource.remote.service.PostService
import es.jaimesuarez.data.extensions.perform
import es.jaimesuarez.data.model.CommentRemote
import es.jaimesuarez.data.model.PostRemote

class PostRemoteDatasource(
    private val postService: PostService
) {

    suspend fun getPostsByUser(userId: Int): Result<List<PostRemote>> {
        return postService.getPostsByUser(userId).perform()
    }

    suspend fun getCommentsByPost(postId: Int): Result<List<CommentRemote>> {
        return postService.getCommentsByPost(postId).perform()
    }
}
