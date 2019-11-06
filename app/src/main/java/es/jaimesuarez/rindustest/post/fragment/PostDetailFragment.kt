package es.jaimesuarez.rindustest.post.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.fragment.BaseFragment
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.util.show
import es.jaimesuarez.rindustest.post.adapter.CommentAdapter
import es.jaimesuarez.rindustest.post.model.CommentDisplay
import es.jaimesuarez.rindustest.post.model.PostDisplay
import es.jaimesuarez.rindustest.post.viewmodel.PostDetailViewModel
import kotlinx.android.synthetic.main.fragment_post_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailFragment : BaseFragment<PostDetailViewModel>() {

    override val viewModel: PostDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_post_detail, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            observePost().observe(viewLifecycleOwner, Observer { showPost(it) })
            observeComments().observe(viewLifecycleOwner, Observer { showComments(it) })
            observeCommentsLoader().observe(viewLifecycleOwner, Observer {
                pb_postDetail_comments_loader.show = it == Visibility.Visible
            })
        }
    }

    private fun showPost(post: PostDisplay) = with(post) {
        tv_postDetail_title.text = title
        tv_postDetail_body.text = body
    }

    private fun showComments(comments: List<CommentDisplay>) = rv_postDetail_comments_list.apply {
        isNestedScrollingEnabled = true
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CommentAdapter(comments.toMutableList())
    }
}
