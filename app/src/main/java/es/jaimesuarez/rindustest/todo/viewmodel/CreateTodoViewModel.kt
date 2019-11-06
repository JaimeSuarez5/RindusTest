package es.jaimesuarez.rindustest.todo.viewmodel

import androidx.lifecycle.LiveData
import es.jaimesuarez.domain.model.Todo
import es.jaimesuarez.domain.usecase.CreateTodoCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.common.util.SingleLiveEvent
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import es.jaimesuarez.rindustest.todo.fragment.TodoListFragmentArgs
import es.jaimesuarez.rindustest.todo.model.TodoDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CreateTodoViewModel(
    private val createTodoCase: CreateTodoCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private var userId: Int = 0

    private val newTodoEvent = SingleLiveEvent<TodoDisplay>()

    fun observeTodoCreation(): LiveData<TodoDisplay> = newTodoEvent

    init {
        loadArguments()
    }

    private fun loadArguments() {
        arguments?.let {
            userId = TodoListFragmentArgs.fromBundle(it).userId
        }
    }

    fun onCreateClick(content: String) = launch {
        val newTodo = Todo(userId = userId, title = content, completed = false)
        when (val result = createTodoCase.create(newTodo)) {
            is Result.Success -> {
                newTodoEvent.value = TodoDisplay(result.data)
                navigateBack()
            }
            is Result.Failure -> notifyError(result.error)
        }
    }
}
