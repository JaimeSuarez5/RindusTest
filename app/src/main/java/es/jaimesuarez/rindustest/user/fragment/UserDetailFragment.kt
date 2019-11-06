package es.jaimesuarez.rindustest.user.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.album.adapter.AlbumAdapter
import es.jaimesuarez.rindustest.common.fragment.BaseFragment
import es.jaimesuarez.rindustest.common.fragment.ItemListFragment
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.util.show
import es.jaimesuarez.rindustest.common.util.showToast
import es.jaimesuarez.rindustest.post.adapter.PostAdapter
import es.jaimesuarez.rindustest.todo.adapter.TodoAdapter
import es.jaimesuarez.rindustest.user.model.UserDisplay
import es.jaimesuarez.rindustest.user.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment<UserDetailViewModel>() {

    override val viewModel: UserDetailViewModel by viewModel()

    private val postsAdapter = PostAdapter { viewModel.onPostClick(it) }
    private val albumsAdapter = AlbumAdapter { viewModel.onAlbumClick(it) }
    private val todosAdapter = TodoAdapter(
        onTodoClick = { showToast(getString(R.string.feature_not_implemented)) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private fun setUi() {
        setTodoRecycler()
        setAlbumRecycler()
        setPostRecycler()

        tv_userDetail_todos_title.setOnClickListener { viewModel.onShowMoreTodosClick() }
        tv_userDetail_albums_title.setOnClickListener {
            viewModel.onShowMoreClick(itemType = ItemListFragment.ListType.ALBUM)
        }
        tv_userDetail_posts_title.setOnClickListener {
            viewModel.onShowMoreClick(itemType = ItemListFragment.ListType.POST)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            observeUser().observe(viewLifecycleOwner, Observer {
                showUser(it)
            })

            observeAlbums().observe(viewLifecycleOwner, Observer { albumsAdapter.submitList(it) })
            observeAlbumsLoader().observe(viewLifecycleOwner, Observer {
                pb_userDetail_albums.show = it == Visibility.Visible
            })

            observePosts().observe(viewLifecycleOwner, Observer { postsAdapter.submitList(it) })
            observePostsLoader().observe(viewLifecycleOwner, Observer {
                pb_userDetail_posts.show = it == Visibility.Visible
            })

            observeTodos().observe(viewLifecycleOwner, Observer { todosAdapter.submitList(it) })
            observeTodosLoader().observe(viewLifecycleOwner, Observer {
                pb_userDetail_todos.show = it == Visibility.Visible
            })
        }
    }

    private fun showUser(user: UserDisplay) = with(user) {
        tv_userDetail_name.text = name
        tv_userDetail_company.text = company
        tv_userDetail_email.text = email
        tv_userDetail_username.text = username
        tv_userDetail_website.text = website
    }

    private fun setPostRecycler() = setRecycler(
        recyclerView = rv_userDetail_posts,
        customAdapter = postsAdapter
    )

    private fun setAlbumRecycler() = setRecycler(
        recyclerView = rv_userDetail_albums,
        customAdapter = albumsAdapter
    )

    private fun setTodoRecycler() = setRecycler(
        recyclerView = rv_userDetail_todos,
        customAdapter = todosAdapter
    )

    private fun <R : RecyclerView.ViewHolder> setRecycler(
        recyclerView: RecyclerView,
        customAdapter: RecyclerView.Adapter<R>
    ) = recyclerView.apply {
        onFlingListener = null
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = customAdapter
        LinearSnapHelper().attachToRecyclerView(this)
    }
}
