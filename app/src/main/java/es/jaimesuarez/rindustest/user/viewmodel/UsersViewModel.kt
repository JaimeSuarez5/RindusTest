package es.jaimesuarez.rindustest.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.GetUserCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import es.jaimesuarez.rindustest.user.fragment.UsersFragmentDirections
import es.jaimesuarez.rindustest.user.model.UserDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUserCase: GetUserCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private val users = MutableLiveData<List<UserDisplay>>()
    private val usersLoader = MutableLiveData<Visibility>(Visibility.Visible)

    fun observeUsers(): LiveData<List<UserDisplay>> = users
    fun observeUsersLoader(): LiveData<Visibility> = usersLoader

    init {
        launch { loadUsers() }
    }

    private suspend fun loadUsers() {
        when (val result = getUserCase.get()) {
            is Result.Success -> users.value = result.data.map { UserDisplay(it) }
            is Result.Failure -> notifyError(result.error)
        }
        usersLoader.value = Visibility.Hidden
    }

    fun onUserClicked(user: UserDisplay) = navigate(
        UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user)
    )
}
