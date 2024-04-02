package br.com.tosin.listgithubusers.ui.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.api.GithubService
import br.com.tosin.listgithubusers.data.model.User
import br.com.tosin.listgithubusers.databinding.FragmentUserDetailBinding
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

        textViewUserDetailNumRepository.text = getString(R.string.num_repository, user.publicRepos)
        textViewUserDetailNumGist.text = getString(R.string.num_gist, user.publicGists)
        textViewUserDetailFollowers.text = getString(R.string.num_follower, user.followers)
        textViewUserDetailFollowings.text = getString(R.string.num_following, user.following)
    }
}
