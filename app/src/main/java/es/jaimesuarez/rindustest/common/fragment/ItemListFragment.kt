package es.jaimesuarez.rindustest.common.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.album.adapter.AlbumAdapter
import es.jaimesuarez.rindustest.album.model.AlbumDisplay
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment.ListType.ALBUM
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment.ListType.POST
import es.jaimesuarez.rindustest.common.viewmodel.ItemListViewModel
import es.jaimesuarez.rindustest.post.adapter.PostAdapter
import es.jaimesuarez.rindustest.post.model.PostDisplay
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_item_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ItemListFragment : BaseFragment<ItemListViewModel>() {

    @Parcelize
    enum class ListType : Parcelable {
        ALBUM, POST
    }

    override val viewModel: ItemListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        arguments?.let {
            when (ItemListFragmentArgs.fromBundle(it).itemType) {
                ALBUM -> setToolbarTitle(R.string.albums)
                POST -> setToolbarTitle(R.string.posts)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.itemList.observe(viewLifecycleOwner, Observer { result ->
            showItems(itemType = result.first, items = result.second)
        })
    }

    private fun setToolbarTitle(@StringRes title: Int) {
        activity?.findViewById<Toolbar>(R.id.toolbar)?.title = getString(title)
    }

    private fun showItems(itemType: ListType, items: List<Any>) {
        rv_list_items.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = when (itemType) {
                ALBUM -> AlbumAdapter(items as MutableList<AlbumDisplay>) { viewModel.onAlbumClick(it) }
                POST -> PostAdapter(items as MutableList<PostDisplay>) { viewModel.onPostClick(it) }
            }
        }
    }
}
