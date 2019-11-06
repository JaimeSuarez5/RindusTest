package es.jaimesuarez.rindustest.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.common.fragment.BaseFragment
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.util.show
import es.jaimesuarez.rindustest.user.adapter.UserAdapter
import es.jaimesuarez.rindustest.user.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.android.viewmodel.ext.android.viewModel


class UsersFragment : BaseFragment<UsersViewModel>() {

    override val viewModel: UsersViewModel by viewModel()

    private val usersAdapter: UserAdapter by lazy {
        UserAdapter { viewModel.onUserClicked(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_users.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            observeUsers().observe(viewLifecycleOwner, Observer { usersAdapter.setUsers(it) })
            observeUsersLoader().observe(viewLifecycleOwner, Observer {
                pb_users_loader.show = it == Visibility.Visible
            })
        }
    }
}
