package br.com.tosin.listgithubusers.ui.user.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.UserPagingSource
import br.com.tosin.listgithubusers.data.model.User
import kotlinx.coroutines.flow.Flow

class UserListViewModel(
    private val repository: GithubServiceDao
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    fun loadUsers(isOnline: Boolean): Flow<PagingData<User>> {
        val pagingSourceFactory = UserPagingSource(
            isOnline = isOnline,
            remoteRepository = repository
        )
        val pager = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                pagingSourceFactory
            }
        )
        return pager.flow
    }
}
