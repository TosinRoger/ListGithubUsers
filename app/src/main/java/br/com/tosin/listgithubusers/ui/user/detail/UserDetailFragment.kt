package br.com.tosin.listgithubusers.ui.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.api.GithubService
import br.com.tosin.listgithubusers.data.model.User
import br.com.tosin.listgithubusers.databinding.FragmentUserDetailBinding
import br.com.tosin.listgithubusers.ui.MainActivity
import br.com.tosin.listgithubusers.ui.utils.defaultDisplayOfStringEmpty
import br.com.tosin.listgithubusers.ui.utils.viewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class UserDetailFragment:Fragment(R.layout.fragment_user_detail) {

    companion object {
        const val ARGS_USERNAME = "args_username"
    }

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserDetailViewModel

    private val username by lazy {
        val aux = requireArguments().getString(ARGS_USERNAME)
        checkNotNull(aux)
        aux
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpView()
        setUpMenu()

        viewModel.fetchUserByName(username = username)
    }

    private fun setUpObservers() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory {
                UserDetailViewModel(
                    repository = GithubService.create()
                )
            }
        )[UserDetailViewModel::class.java]

        viewModel.user.observe(viewLifecycleOwner) {
            setUpViewWithUser(it)
        }
    }

    private fun setUpView() {
        (requireActivity() as? MainActivity)?.let { mainActivity ->
            mainActivity.setSupportActionBar(_binding?.toolbar)
            mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
            mainActivity.supportActionBar?.setDisplayShowTitleEnabled(true)
        }
        _binding?.toolbar?.title = ""
    }

    private fun setUpMenu() {
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                    menuInflater.inflate(R.menu.menu_user_detail, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        android.R.id.home -> {
                            findNavController().popBackStack()
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

    private fun setUpViewWithUser(user: User) = binding.run {
        Glide
            .with(requireContext())
            .load(user.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_user_rounded)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageViewUserDetailAvatar)

        textViewUserDetailName.text = user.login
        textViewUserDetailLocation.text = user.location.defaultDisplayOfStringEmpty()
        textViewUserDetailFollowers.text = user.followers.toString()
        textViewUserDetailFollowings.text = user.following.toString()
    }
}
