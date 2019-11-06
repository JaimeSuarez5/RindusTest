package es.jaimesuarez.rindustest.common.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import es.jaimesuarez.domain.util.ErrorKind
import es.jaimesuarez.rindustest.common.util.NavigationCommand
import es.jaimesuarez.rindustest.common.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    val errorCommand = SingleLiveEvent<ErrorKind>()

    var arguments: Bundle? = null

    protected fun navigate(directions: NavDirections) {
        navigationCommands.postValue(NavigationCommand.To(directions))
    }

    protected fun navigateBack() {
        navigationCommands.postValue(NavigationCommand.Back)
    }

    protected fun notifyError(error: ErrorKind) {
        errorCommand.postValue(error)
    }
}
