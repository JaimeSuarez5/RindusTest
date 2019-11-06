package es.jaimesuarez.rindustest.todo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.util.inflate
import es.jaimesuarez.rindustest.todo.model.TodoDisplay
import es.jaimesuarez.rindustest.todo.util.TodoDiffCallback

class TodoAdapter(
    private val onTodoClick: (TodoDisplay) -> Unit,
    private val onTodoSwipe: ((Int) -> Unit)? = null
) : ListAdapter<TodoDisplay, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TodoViewHolder(
        parent.inflate(R.layout.item_todo)
    )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<TodoDisplay>?) {
        super.submitList(if (list != null) listOf(*list.toTypedArray()) else null)
    }

    fun onSwipeItem(position: Int) {
        onTodoSwipe?.invoke(position)
    }

    inner class TodoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(todo: TodoDisplay) = view.apply {
            setOnClickListener { onTodoClick(todo) }
            findViewById<CheckBox>(R.id.cb_todo_checked).isChecked = todo.completed
            findViewById<TextView>(R.id.tv_todo_title).text = todo.title
        }
    }
}
