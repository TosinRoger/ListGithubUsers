package br.com.tosin.listgithubusers.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.api.GithubService
import br.com.tosin.listgithubusers.databinding.FragmentUserListBinding
import br.com.tosin.listgithubusers.ui.dialog.InformationAlertDialog
import br.com.tosin.listgithubusers.ui.user.detail.UserDetailFragment
import br.com.tosin.listgithubusers.ui.utils.loadstate.LoadStateFooterAdapter
import br.com.tosin.listgithubusers.ui.user.list.adapter.UserAdapter
import br.com.tosin.listgithubusers.ui.utils.viewModelFactory
import br.com.tosin.listgithubusers.utils.checkIsOnline
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserListViewModel
    private lateinit var mAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpView()
    }

    private fun setUpObservers() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory {
                UserListViewModel(
                    GithubService.create()
                )
            }
        )[UserListViewModel::class.java]
    }

    private fun setUpView() {
        mAdapter = UserAdapter { username ->
            openUserDetails(username)
        }

        mAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            val showList = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            val showLoading = loadState.source.refresh is LoadState.Loading

            val hasError = loadState.source.refresh is LoadState.Error

            if (hasError) {
                showEmptyList(true)
                showError("Error !!!!")
            }

            // here only hide loading. The show is in launch search/load
            if (showLoading) {
                showEmptyList(false)
            } else if (showList && mAdapter.itemCount == 0) {
                showEmptyList(true)
            } else if (showList && mAdapter.itemCount > 0) {
                showEmptyList(false)
            }
        }

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

        val concatAdapter = mAdapter.withLoadStateFooter(LoadStateFooterAdapter())

        binding.recyclerViewUserList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
            addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            viewModel.loadUsers(isOnline = checkIsOnline(requireContext())).collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun showError(msg: String) {
        InformationAlertDialog(
            title = getString(R.string.alert),
            msg = msg
        ).show(childFragmentManager, "WorkDetailShowError")
    }

    private fun showEmptyList(show: Boolean) {
        binding.placeholderEmptyList.root.isVisible = show
    }

    private fun openUserDetails(username: String) {
        val args = Bundle().apply {
            putString(UserDetailFragment.ARGS_USERNAME, username)
        }

        findNavController().navigate(R.id.action_userListFragment_to_userDetailFragment, args)
    }
}
