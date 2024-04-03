@file:Suppress("TooGenericExceptionCaught", "SwallowedException")

package br.com.tosin.listgithubusers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.User
import retrofit2.HttpException

class UserPagingSource(
    private val remoteRepository: GithubServiceDao,
    private val searchUser: String = ""
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
            val position = params.key ?: STARTING_PAGE_INDEX

            var response: List<User>
            if (searchUser.isEmpty()) {
                response = remoteRepository.getUsers(position).map { it.asModel() }
            } else {
                try {
                    val userRemote = remoteRepository.fetchUserByUsername(searchUser)
                    response = listOf(userRemote.asModel())
                } catch (e: HttpException) {
                    response = emptyList()

                    return LoadResult.Error(e)
                }
            }

            val nextKey = if (response.isEmpty() || searchUser.isNotEmpty()) {
                null
            } else {
                response.last().id + 1
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
