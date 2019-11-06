package es.jaimesuarez.rindustest.todo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.fragment.BaseDialogFragment
import es.jaimesuarez.rindustest.todo.viewmodel.CreateTodoViewModel
import kotlinx.android.synthetic.main.dialog_create_todo.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CreateTodoDialog : BaseDialogFragment<CreateTodoViewModel>() {

    override val viewModel: CreateTodoViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_create_todo, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_createTodo_content.requestFocus()
        btn_createTodo_create.setOnClickListener {
            val content = et_createTodo_content.text
            if (!content.isNullOrEmpty()) viewModel.onCreateClick(content.toString())
        }
    }
}
