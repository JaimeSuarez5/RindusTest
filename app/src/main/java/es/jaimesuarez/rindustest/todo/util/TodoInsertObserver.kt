package es.jaimesuarez.rindustest.todo.util

import androidx.recyclerview.widget.RecyclerView

/**
 * Observes the inserts to the adapter and notifies when a single item is added to scroll to
 * the first position.
 */
class TodoInsertObserver(
    private val recyclerView: RecyclerView
) : RecyclerView.AdapterDataObserver() {
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        if (positionStart == 0 && itemCount == 1) recyclerView.scrollToPosition(0)
    }
}