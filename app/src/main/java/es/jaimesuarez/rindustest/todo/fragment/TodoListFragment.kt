package es.jaimesuarez.rindustest.todo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.fragment.BaseFragment
import es.jaimesuarez.rindustest.common.util.SwipeToDeleteCallback
import es.jaimesuarez.rindustest.common.util.showSnackbar
import es.jaimesuarez.rindustest.common.util.showToast
import es.jaimesuarez.rindustest.todo.adapter.TodoAdapter
import es.jaimesuarez.rindustest.todo.util.TodoInsertObserver
import es.jaimesuarez.rindustest.todo.viewmodel.CreateTodoViewModel
import es.jaimesuarez.rindustest.todo.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_item_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TodoListFragment : BaseFragment<TodoListViewModel>() {

    override val viewModel: TodoListViewModel by viewModel()

    private val createTodoSharedViewModel: CreateTodoViewModel by sharedViewModel()

    private var todoAdapter: TodoAdapter = TodoAdapter(
        onTodoClick = {
            showToast(getString(R.string.feature_not_implemented))
        },
        onTodoSwipe = { position ->
            viewModel.onSwipeTodo(position)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private fun setUi() {
        fab_add_item.apply {
            show()
            setOnClickListener { viewModel.onNewTodoClick() }
        }
        rv_list_items.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
        todoAdapter.registerAdapterDataObserver(TodoInsertObserver(rv_list_items))
        ItemTouchHelper(SwipeToDeleteCallback(todoAdapter)).attachToRecyclerView(rv_list_items)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observeTodos().observe(viewLifecycleOwner, Observer { todoAdapter.submitList(it) })
        viewModel.observeItemDeletionResult().observe(viewLifecycleOwner, Observer { isDeleted ->
            if (isDeleted) {
                view?.findViewById<View>(R.id.cl_item_list)?.let {
                    showSnackbar(
                        parentView = it,
                        content = getString(R.string.todo_deleted),
                        colorRes = R.color.colorAccent
                    )
                }
            }
        })
        createTodoSharedViewModel.observeTodoCreation().observe(
            viewLifecycleOwner,
            Observer { todo ->
                viewModel.onTodoCreated(todo)
                view?.findViewById<View>(R.id.cl_item_list)?.let {
                    showSnackbar(
                        parentView = it,
                        content = getString(R.string.new_todo_created)
                    )
                }
            }
        )
    }
}
