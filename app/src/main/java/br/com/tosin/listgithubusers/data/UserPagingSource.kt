package br.com.tosin.listgithubusers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.User

class UserPagingSource(private val remoteRepository: GithubServiceDao): PagingSource<Int, User>() {

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
        val position = params.key ?: STARTING_PAGE_INDEX

        val response = remoteRepository.getUsers(

        ).map { it.asModel() }
        val nextKey = if (response.isEmpty()) {
            null
        } else {
            position + 1
        }
        return LoadResult.Page(
            data = response,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = nextKey
        )
    }
}