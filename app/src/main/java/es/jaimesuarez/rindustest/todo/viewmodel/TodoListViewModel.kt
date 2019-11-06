package es.jaimesuarez.rindustest.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.DeleteTodoCase
import es.jaimesuarez.domain.usecase.GetTodosByUserCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.common.util.SingleLiveEvent
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import es.jaimesuarez.rindustest.todo.fragment.TodoListFragmentArgs
import es.jaimesuarez.rindustest.todo.fragment.TodoListFragmentDirections
import es.jaimesuarez.rindustest.todo.model.TodoDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val getTodosByUserCase: GetTodosByUserCase,
    private val deleteTodoCase: DeleteTodoCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private var userId: Int = 0

    private val todos = MutableLiveData<MutableList<TodoDisplay>>()
    private val itemDeletionResult = SingleLiveEvent<Boolean>()

    fun observeTodos(): LiveData<MutableList<TodoDisplay>> = todos
    fun observeItemDeletionResult(): LiveData<Boolean> = itemDeletionResult

    init {
        launch {
            loadArguments()
            loadTodos()
        }
    }

    private fun loadArguments() {
        arguments?.let {
            userId = TodoListFragmentArgs.fromBundle(it).userId
        }
    }

    private suspend fun loadTodos() {
        when (val result = getTodosByUserCase.get(userId)) {
            is Result.Success -> todos.value = result.data.map { TodoDisplay(it) }.toMutableList()
            is Result.Failure -> notifyError(result.error)
        }
    }

    fun onTodoCreated(todo: TodoDisplay) {
        todos.value = todos.value?.toMutableList()?.apply { add(0, todo) }
    }

    fun onNewTodoClick() = navigate(
        TodoListFragmentDirections.actionTodoListFragmentToCreateTodoDialog(userId)
    )

    fun onSwipeTodo(position: Int) = launch {
        val swipedTodo = todos.value?.get(position)
        swipedTodo?.id?.let { todoId ->
            when (val result = deleteTodoCase.delete(todoId)) {
                is Result.Success -> {
                    todos.value = todos.value?.apply { removeAt(position) }
                    itemDeletionResult.value = true
                }
                is Result.Failure -> notifyError(result.error)
            }
            itemDeletionResult.value = false
        }
    }
}