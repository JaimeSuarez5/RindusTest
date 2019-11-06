package es.jaimesuarez.rindustest.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.GetAlbumsByUserCase
import es.jaimesuarez.domain.usecase.GetPostsByUserCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.album.model.AlbumDisplay
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment.ListType.ALBUM
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment.ListType.POST
import es.jaimesuarez.rindustest.common.fragment.ItemListFragmentArgs
import es.jaimesuarez.rindustest.common.fragment.ItemListFragmentDirections
import es.jaimesuarez.rindustest.post.model.PostDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getAlbumsByUserCase: GetAlbumsByUserCase,
    private val getPostsByUserCase: GetPostsByUserCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private var userId: Int = 0
    private lateinit var itemType: ItemListFragment.ListType

    private val items = MutableLiveData<Pair<ItemListFragment.ListType, List<Any>>>()

    val itemList: LiveData<Pair<ItemListFragment.ListType, List<Any>>> = items

    init {
        launch {
            readArguments()
            loadItems()
        }
    }

    private fun readArguments() {
        arguments?.let {
            val args = ItemListFragmentArgs.fromBundle(it)
            userId = args.userId
            itemType = args.itemType
        }
    }

    private suspend fun loadItems() {
        when (val result = when (itemType) {
            ALBUM -> getAlbumsByUserCase.get(userId).mapResult { it.map { AlbumDisplay(it) } }
            POST -> getPostsByUserCase.get(userId).mapResult { it.map { PostDisplay(it) } }
        }) {
            is Result.Success -> items.value = Pair(itemType, result.data)
            is Result.Failure -> notifyError(result.error)
        }
    }

    fun onPostClick(post: PostDisplay) = navigate(
        ItemListFragmentDirections.actionItemListFragmentToPostDetailFragment(post)
    )

    fun onAlbumClick(album: AlbumDisplay) = navigate(
        ItemListFragmentDirections.actionItemListFragmentToAlbumDetailFragment(album, album.title)
    )
}
