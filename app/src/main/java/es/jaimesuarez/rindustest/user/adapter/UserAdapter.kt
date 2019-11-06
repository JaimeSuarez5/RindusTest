package es.jaimesuarez.rindustest.user.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.util.inflate
import es.jaimesuarez.rindustest.user.model.UserDisplay

class UserAdapter(
    private val onUserClick: (UserDisplay) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: MutableList<UserDisplay> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.item_user))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(users[position])
    }

    fun setUsers(users: List<UserDisplay>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(user: UserDisplay) = view.apply {
            findViewById<ConstraintLayout>(R.id.cl_user_item).setOnClickListener {
                this@UserAdapter.onUserClick(user)
            }

            with(user) {
                findViewById<TextView>(R.id.tv_user_name).text = name
                findViewById<TextView>(R.id.tv_user_username).text = username
                findViewById<TextView>(R.id.tv_user_company).text = company
            }
        }
    }
}
