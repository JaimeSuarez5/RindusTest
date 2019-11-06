package es.jaimesuarez.rindustest.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.GetCommentsByPostCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import es.jaimesuarez.rindustest.post.fragment.PostDetailFragmentArgs
import es.jaimesuarez.rindustest.post.model.CommentDisplay
import es.jaimesuarez.rindustest.post.model.PostDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val getCommentsByPostCase: GetCommentsByPostCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private val post = MutableLiveData<PostDisplay>()
    private val comments = MutableLiveData<List<CommentDisplay>>()
    private val commentsLoader = MutableLiveData<Visibility>(Visibility.Visible)

    fun observePost(): LiveData<PostDisplay> = post
    fun observeComments(): LiveData<List<CommentDisplay>> = comments
    fun observeCommentsLoader(): LiveData<Visibility> = commentsLoader

    init {
        launch {
            showPost()
            loadComments()
        }
    }

    private fun showPost() {
        arguments?.let {
            post.value = PostDetailFragmentArgs.fromBundle(it).post
        }
    }

    private suspend fun loadComments() {
        post.value?.id?.let { postId ->
            when (val result = getCommentsByPostCase.get(postId)) {
                is Result.Success -> comments.value = result.data.map { CommentDisplay(it) }
                is Result.Failure -> notifyError(result.error)
            }
            commentsLoader.value = Visibility.Hidden
        }
    }
}
