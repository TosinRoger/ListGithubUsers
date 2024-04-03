package br.com.tosin.listgithubusers.ui.user.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.UserRepo
import br.com.tosin.listgithubusers.data.model.User
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val repository: GithubServiceDao
) : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _userRepo = MutableLiveData<List<UserRepo>>()
    val userRepo: LiveData<List<UserRepo>>
        get() = _userRepo

    private var _errorNoNetwork = MutableLiveData<Boolean>()
    val errorNoNetwork: LiveData<Boolean>
        get() = _errorNoNetwork

    fun fetchUserAndSetUpView(isOnline: Boolean, username: String) {
        if (isOnline) {
            viewModelScope.launch {
                fetchUserByName(username = username)
                fetchRepositoriesFromUser(username = username)
            }
        } else {
            _errorNoNetwork.postValue(true)
        }
    }

    private suspend fun fetchUserByName(username: String) {
        val userRemote = repository.fetchUserByUsername(username = username)
        val user = userRemote.asModel()
        _user.postValue(user)
    }

    private suspend fun fetchRepositoriesFromUser(username: String) {
        val userReposRemote = repository.fetchRepositoryFromUser(username = username)
        val userRepos = userReposRemote.map { it.asModel() }
        _userRepo.postValue(userRepos)
    }
}
