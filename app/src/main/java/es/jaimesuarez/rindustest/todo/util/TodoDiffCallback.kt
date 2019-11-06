package es.jaimesuarez.rindustest.todo.util

import androidx.recyclerview.widget.DiffUtil
import es.jaimesuarez.rindustest.todo.model.TodoDisplay

class TodoDiffCallback : DiffUtil.ItemCallback<TodoDisplay>() {

    override fun areContentsTheSame(oldItem: TodoDisplay, newItem: TodoDisplay): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: TodoDisplay, newItem: TodoDisplay): Boolean {
        return oldItem.id == newItem.id
    }
}
