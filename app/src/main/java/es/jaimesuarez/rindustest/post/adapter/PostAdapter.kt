package es.jaimesuarez.rindustest.post.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.util.inflate
import es.jaimesuarez.rindustest.post.model.PostDisplay

class PostAdapter(
    private val posts: MutableList<PostDisplay> = mutableListOf(),
    private val onPostClick: (PostDisplay) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(parent.inflate(R.layout.item_post))
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(posts[position])
    }

    fun submitList(newPosts: List<PostDisplay>) {
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    inner class PostViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(post: PostDisplay) = view.apply {
            view.setOnClickListener { onPostClick(post) }
            with(post) {
                findViewById<TextView>(R.id.tv_post_title).text = title
                findViewById<TextView>(R.id.tv_post_body).text = body
            }
        }
    }
}
