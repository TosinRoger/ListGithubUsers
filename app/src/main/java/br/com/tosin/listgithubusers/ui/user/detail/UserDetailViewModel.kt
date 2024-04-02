package br.com.tosin.listgithubusers.ui.user.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.User
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val repository: GithubServiceDao
): ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    fun fetchUserByName(username: String) {
        viewModelScope.launch {
            val userRemote = repository.fetchUserByUsername(username = username)

            val user = userRemote.asModel()
            _user.value = user
        }
    }
}