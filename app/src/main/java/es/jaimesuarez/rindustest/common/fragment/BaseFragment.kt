package es.jaimesuarez.rindustest.common.fragment

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.domain.util.ErrorKind
import es.jaimesuarez.rindustest.common.util.NavigationCommand
import es.jaimesuarez.rindustest.common.util.showSnackbar
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.arguments = arguments
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            navigationCommands.observe(viewLifecycleOwner, Observer { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.Back -> findNavController().popBackStack()
                }
            })
            errorCommand.observe(viewLifecycleOwner, Observer { error ->
                error.exception?.printStackTrace()
                activity?.findViewById<LinearLayout>(R.id.ll_root_layout)?.let {
                    showSnackbar(
                        parentView = it,
                        content = getErrorMessage(error),
                        colorRes = R.color.error_red
                    )
                }
            })
        }
    }

    private fun getErrorMessage(errorKind: ErrorKind): String {
        return getString(
            when (errorKind) {
                is ErrorKind.ApiError -> R.string.api_error
                is ErrorKind.CacheError -> R.string.cache_error
                is ErrorKind.UnknownError -> R.string.unknown_error
            }
        )
    }
}
