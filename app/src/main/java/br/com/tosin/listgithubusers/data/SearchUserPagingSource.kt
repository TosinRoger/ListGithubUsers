@file:Suppress("TooGenericExceptionCaught")

package br.com.tosin.listgithubusers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.User

class SearchUserPagingSource(
    private val isOnline: Boolean,
    private val remoteRepository: GithubServiceDao,
    private val searchUser: String
) : PagingSource<Int, User>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            if (isOnline) {
                val position = params.key ?: STARTING_PAGE_INDEX
                val userRemote = remoteRepository.fetchUserByUsername(searchUser)

                val responseList = mutableListOf<User>()

                val user = userRemote.asModel()
                responseList.add(user)

                val nextKey = if (responseList.isEmpty()) {
                    null
                } else {
                    responseList.last().id + 1
                }
                LoadResult.Page(
                    data = responseList,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("No network"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
