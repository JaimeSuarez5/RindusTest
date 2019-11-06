package es.jaimesuarez.rindustest.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.GetAlbumsByUserCase
import es.jaimesuarez.domain.usecase.GetPostsByUserCase
import es.jaimesuarez.domain.usecase.GetTodosByUserCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.album.model.AlbumDisplay
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import es.jaimesuarez.rindustest.post.model.PostDisplay
import es.jaimesuarez.rindustest.todo.model.TodoDisplay
import es.jaimesuarez.rindustest.user.fragment.UserDetailFragmentArgs
import es.jaimesuarez.rindustest.user.fragment.UserDetailFragmentDirections
import es.jaimesuarez.rindustest.user.model.UserDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getAlbumsByUserCase: GetAlbumsByUserCase,
    private val getPostsByUserCase: GetPostsByUserCase,
    private val getTodosByUserCase: GetTodosByUserCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private val user = MutableLiveData<UserDisplay>()
    fun observeUser(): LiveData<UserDisplay> = user

    private val albums = MutableLiveData<List<AlbumDisplay>>()
    fun observeAlbums(): LiveData<List<AlbumDisplay>> = albums

    private val todos = MutableLiveData<List<TodoDisplay>>()
    fun observeTodos(): LiveData<List<TodoDisplay>> = todos

    private val posts = MutableLiveData<List<PostDisplay>>()
    fun observePosts(): LiveData<List<PostDisplay>> = posts

    private val albumsLoader = MutableLiveData<Visibility>(Visibility.Visible)
    fun observeAlbumsLoader(): LiveData<Visibility> = albumsLoader

    private val todosLoader = MutableLiveData<Visibility>(Visibility.Visible)
    fun observeTodosLoader(): LiveData<Visibility> = todosLoader

    private val postsLoader = MutableLiveData<Visibility>(Visibility.Visible)
    fun observePostsLoader(): LiveData<Visibility> = postsLoader

    init {
        launch {
            loadArguments()
            loadUsers()
        }
    }

    private suspend fun loadUsers() = user.value?.let { user ->
        fetchAlbums(user.id)
        fetchPosts(user.id)
        fetchTodos(user.id)
    }

    private fun loadArguments() {
        val detailedUser = arguments?.let { UserDetailFragmentArgs.fromBundle(it).userInfo }
        user.value = detailedUser
    }

    private suspend fun fetchAlbums(userId: Int) =
        when (val result = getAlbumsByUserCase.get(userId)) {
            is Result.Success -> albums.value = result.data.map { AlbumDisplay(it) }.take(4)
            is Result.Failure -> notifyError(result.error)
        }.also { albumsLoader.value = Visibility.Hidden }

    private suspend fun fetchPosts(userId: Int) =
        when (val result = getPostsByUserCase.get(userId)) {
            is Result.Success -> posts.value = result.data.map { PostDisplay(it) }.take(4)
            is Result.Failure -> notifyError(result.error)
        }.also { postsLoader.value = Visibility.Hidden }

    private suspend fun fetchTodos(userId: Int) =
        when (val result = getTodosByUserCase.get(userId)) {
            is Result.Success -> todos.value = result.data.map { TodoDisplay(it) }.take(4)
            is Result.Failure -> notifyError(result.error)
        }.also { todosLoader.value = Visibility.Hidden }

    fun onShowMoreClick(itemType: ItemListFragment.ListType) = user.value?.id?.let { userId ->
        navigate(
            UserDetailFragmentDirections.actionUserDetailFragmentToItemListFragment(
                userId = userId,
                itemType = itemType
            )
        )
    }

    fun onShowMoreTodosClick() = user.value?.id?.let { userId ->
        navigate(
            UserDetailFragmentDirections.actionUserDetailFragmentToTodoListFragment(
                userId = userId
            )
        )
    }

    fun onAlbumClick(album: AlbumDisplay) = navigate(
        UserDetailFragmentDirections.actionUserDetailFragmentToAlbumDetailFragment(
            album = album,
            albumName = album.title
        )
    )

    fun onPostClick(post: PostDisplay) = navigate(
        UserDetailFragmentDirections.actionUserDetailFragmentToPostDetailFragment(post)
    )
}
