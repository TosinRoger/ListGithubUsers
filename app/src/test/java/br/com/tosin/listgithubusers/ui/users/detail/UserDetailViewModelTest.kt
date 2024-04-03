package br.com.tosin.listgithubusers.ui.users.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.tosin.listgithubusers.api.GithubServiceDao
import br.com.tosin.listgithubusers.api.entity.UserRemote
import br.com.tosin.listgithubusers.api.entity.UserRepoRemote
import br.com.tosin.listgithubusers.data.mapper.asModel
import br.com.tosin.listgithubusers.ui.user.detail.UserDetailViewModel
import br.com.tosin.listgithubusers.utils.DataFactory
import br.com.tosin.listgithubusers.utils.MainCoroutineRule
import br.com.tosin.listgithubusers.utils.entity.UserRepoRemoteFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {

    @Rule
    @JvmField
    var mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UserDetailViewModel

    @MockK
    private lateinit var repository: GithubServiceDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = UserDetailViewModel(repository = repository)
    }

    @Test
    fun `no network`() = runTest {
        // GIVEN
        val isOnline = false
        val username = DataFactory.randomString()

        // WHEN
        viewModel.fetchUserAndSetUpView(isOnline = isOnline, username = username)
        advanceUntilIdle()

        // THEN
        val showError = viewModel.errorNoNetwork.value
        assertNotNull(showError)
        assertTrue(showError)
    }

    @Test
    fun `try load data - is off-line`() = runTest {
        // GIVEN
        val isOnline = false
        val userRepoRemote = UserRepoRemoteFactory.makeFull()
        val userRemote = userRepoRemote.owner

        // WHEN
        viewModel.fetchUserAndSetUpView(isOnline = isOnline, username = userRemote.login)
        advanceUntilIdle()

        // THEN
        val error = viewModel.errorNoNetwork.value
        assertNotNull(error)
        assertTrue(error)
    }

    @Test
    fun `try load data - is on-line`() = runTest {
        // GIVEN
        val isOnline = true
        val userRepoRemote = UserRepoRemoteFactory.makeFull()
        val userRemote = userRepoRemote.owner

        stubUserRepo(
            responseUserRemote = userRemote,
            responseUserRepo = listOf(userRepoRemote)
        )

        // WHEN
        viewModel.fetchUserAndSetUpView(isOnline = isOnline, username = userRemote.login)
        advanceUntilIdle()

        // THEN
        val userResponse = viewModel.user.value
        assertNotNull(userResponse)
        assertEquals(userRemote.asModel(), userResponse)

        val userRepoResponse = viewModel.userRepo.value
        assertNotNull(userRepoResponse)
        assertEquals(userRepoRemote.asModel(), userRepoResponse.first())
    }

    private fun stubUserRepo(
        responseUserRemote: UserRemote,
        responseUserRepo: List<UserRepoRemote>
    ) {
        coEvery {
            repository.fetchUserByUsername(username = any())
        } returns responseUserRemote

        coEvery {
            repository.fetchRepositoryFromUser(username = any())
        } returns responseUserRepo
    }
}
