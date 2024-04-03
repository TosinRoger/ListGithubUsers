package br.com.tosin.listgithubusers.ui.user.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
import br.com.tosin.listgithubusers.ui.MainActivity
import br.com.tosin.listgithubusers.ui.dialog.InformationAlertDialog
import br.com.tosin.listgithubusers.ui.user.detail.UserDetailFragment
import br.com.tosin.listgithubusers.ui.user.list.adapter.UserAdapter
import br.com.tosin.listgithubusers.ui.utils.loadstate.LoadStateFooterAdapter
import br.com.tosin.listgithubusers.ui.utils.viewModelFactory
import br.com.tosin.listgithubusers.utils.checkIsOnline
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserListViewModel
    private lateinit var mAdapter: UserAdapter

    private var isSearch = false

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
        setUpMenu()
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
        (requireActivity() as? MainActivity)?.let { mainActivity ->
            mainActivity.setSupportActionBar(_binding?.toolbarUserList)
            mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
            mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        }

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
                val error = loadState.source.refresh as LoadState.Error
                showError(error.error.localizedMessage ?: getString(R.string.no_network))

                showEmptyList(show = !isSearch)
                showEmptySearch(show = isSearch)
            }

            // here only hide loading. The show is in launch search/load
            if (showLoading) {
                showEmptyList(show = false)
                showEmptySearch(show = false)
            } else if (showList && mAdapter.itemCount == 0) {
                if (isSearch) {
                    showEmptyList(show = false)
                    showEmptySearch(show = true)
                } else {
                    showEmptyList(show = true)
                    showEmptySearch(show = false)
                }
            } else if (showList && mAdapter.itemCount > 0) {
                showEmptyList(show = false)
                showEmptySearch(show = false)
            }
        }

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

        val concatAdapter = mAdapter.withLoadStateFooter(LoadStateFooterAdapter())

        binding.recyclerViewUserList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
            addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            if (checkIsOnline(requireContext())) {
                viewModel.loadUsers().collectLatest {
                    mAdapter.submitData(it)
                }
            } else {
                showError(getString(R.string.no_network))
            }
        }
    }

    private fun setUpMenu() {
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_user_list, menu)
                    configTextSearch(menu.findItem(R.id.action_search))
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.menu.menu_user_list -> {
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    private fun configTextSearch(searchItem: MenuItem?) {
        val searchManager = requireActivity().getSystemService(
            Context.SEARCH_SERVICE
        ) as SearchManager
        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        searchView?.setSearchableInfo(
            searchManager.getSearchableInfo(requireActivity().componentName)
        )
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                isSearch = true
                lifecycleScope.launch {
                    if (checkIsOnline(requireContext())) {
                        viewModel.loadUsers(
                            searchUser = s.trim()
                        ).collectLatest {
                            mAdapter.submitData(it)
                        }
                    } else {
                        showError(getString(R.string.no_network))
                    }
                }
                return false
            }
        })
        searchView?.setOnCloseListener {
            isSearch = false
            if (checkIsOnline(requireContext())) {
                lifecycleScope.launch {
                    viewModel.loadUsers().collectLatest {
                        mAdapter.submitData(it)
                    }
                }
            } else {
                showError(getString(R.string.no_network))
            }
            false
        }

    }

    private fun showError(msg: String) {
        InformationAlertDialog(
            title = getString(R.string.alert),
            msg = msg
        ).show(childFragmentManager, "UserList")
    }

    private fun showEmptyList(show: Boolean) {
        binding.placeholderEmptyList.root.isVisible = show
    }

    private fun showEmptySearch(show: Boolean) {
        binding.placeholderEmptySearch.root.isVisible = show
    }


    private fun openUserDetails(username: String) {
        val args = Bundle().apply {
            putString(UserDetailFragment.ARGS_USERNAME, username)
        }

        findNavController().navigate(R.id.action_userListFragment_to_userDetailFragment, args)
    }
}
