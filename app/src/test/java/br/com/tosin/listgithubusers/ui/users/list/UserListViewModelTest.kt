package br.com.tosin.listgithubusers.ui.users.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.api.entity.UserRemote
import br.com.tosin.listgithubusers.data.UserPagingSource
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.data.model.User
import br.com.tosin.listgithubusers.ui.user.list.UserListViewModel
import br.com.tosin.listgithubusers.utils.MainCoroutineRule
import br.com.tosin.listgithubusers.utils.entity.UserRemoteFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    @Rule
    @JvmField
    var mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UserListViewModel
    private lateinit var userPagingSource: UserPagingSource

    @MockK
    private lateinit var repository: GithubServiceDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = UserListViewModel(repository = repository)
        userPagingSource = UserPagingSource(isOnline = true, remoteRepository = repository)
    }

    @Test
    fun `test load empty list`() = runTest {
        // GIVEN
        val userRemoteList = emptyList<UserRemote>()
        val responseUserList = emptyList<User>()
        stubGetUsersRemote(userRemoteList)

        // WHEN
        viewModel.loadUsers(isOnline = true)

        // THEN
        val expected = PagingSource.LoadResult.Page(
            data = responseUserList,
            prevKey = null,
            nextKey = null
        )

        assertEquals(
            userPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
            expected
        )
    }

    @Test
    fun `test load items`() = runTest {
        // GIVEN
        val userRemoteList = listOf(UserRemoteFactory.makeFull())
        val responseUserList = userRemoteList.map { it.asModel() }
        stubGetUsersRemote(userRemoteList)

        // WHEN
        viewModel.loadUsers(isOnline = true)

        // THEN
        val expected = PagingSource.LoadResult.Page(
            data = responseUserList,
            prevKey = null,
            nextKey = responseUserList.last().id + 1
        )

        assertEquals(
            userPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
            expected
        )
    }

    private fun stubGetUsersRemote(remoteUserList: List<UserRemote>) {
        coEvery {
            repository.getUsers(any())
        } returns remoteUserList
    }
}
