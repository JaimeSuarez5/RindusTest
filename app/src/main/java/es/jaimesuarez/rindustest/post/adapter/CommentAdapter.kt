package es.jaimesuarez.rindustest.post.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.util.inflate
import es.jaimesuarez.rindustest.post.model.CommentDisplay

class CommentAdapter(
    private var comments: MutableList<CommentDisplay> = mutableListOf()
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(parent.inflate(R.layout.item_comment))
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.onBind(comments[position])
    }

    inner class CommentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(comment: CommentDisplay) = view.apply {
            with(comment) {
                findViewById<TextView>(R.id.tv_comment_email).text = email
                findViewById<TextView>(R.id.tv_comment_name).text = name
                findViewById<TextView>(R.id.tv_comment_body).text = body
            }
        }
    }
}
