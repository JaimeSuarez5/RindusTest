package es.jaimesuarez.rindustest.common.fragment

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import es.jaimesuarez.rindustest.common.util.NavigationCommand
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel

abstract class BaseDialogFragment<T : BaseViewModel> : DialogFragment() {

    abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.arguments = arguments
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigationCommands.observe(viewLifecycleOwner, Observer { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
                is NavigationCommand.Back -> findNavController().popBackStack()
            }
        })
    }

}
