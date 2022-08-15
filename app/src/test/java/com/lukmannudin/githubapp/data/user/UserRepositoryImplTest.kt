package com.lukmannudin.githubapp.data.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.lukmannudin.githubapp.DumpData
import com.lukmannudin.githubapp.MainCoroutineRule
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.user.local.UserLocalDataSource
import com.lukmannudin.githubapp.data.user.remote.UserRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class UserRepositoryImplTest {

    @Mock
    private lateinit var userLocalDataSource: UserLocalDataSource

    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private lateinit var userRepository: UserRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(
            userRemoteDataSource,
            userLocalDataSource
        )
    }

    @Test
    fun search() = runTest {
        val username = "username"
        val page = 0

        `when`(userRemoteDataSource.search(username, page))
            .thenReturn(Result.Success(mutableListOf(DumpData.getUser())))

        val loadingResult = userRepository.search(username, page).first()
        assertThat(loadingResult is Result.Loading)

        val successResult = userRepository.search(username, page).last()
        assertThat(successResult is Result.Success)

        // when failure scenario
        `when`(userRemoteDataSource.search(username, page))
            .thenReturn(Result.Error(Exception()))

        val loadingResultIOnError = userRepository.search(username, page).first()
        assertThat(loadingResultIOnError is Result.Loading)

        val failureResult = userRepository.search(username, page).last()
        assertThat(failureResult is Result.Error)
    }

    @Test
    fun fetchUserLocal() = runTest {
        val username = "username"

        `when`(userLocalDataSource.getUser(username))
            .thenReturn(Result.Success(DumpData.getUser()))

        val loadingResult = userRepository.fetchUser(username).first()
        assertThat(loadingResult is Result.Loading)

        val successResult = userRepository.fetchUser(username).last()
        assertThat(successResult is Result.Success)

        // when failure scenario
        `when`(userLocalDataSource.getUser(username))
            .thenReturn(Result.Error(Exception()))

        val loadingResultOnError = userRepository.fetchUser(username).first()
        assertThat(loadingResultOnError is Result.Loading)

        val failureResult = userRepository.fetchUser(username).last()
        assertThat(failureResult is Result.Success)
    }

    @Test
    fun fetchUserRemote() = runTest {
        val username = "username"

        `when`(userRemoteDataSource.getUser(username))
            .thenReturn(Result.Success(DumpData.getUser()))

        val loadingResult = userRepository.fetchUser(username).first()
        assertThat(loadingResult is Result.Loading)

        val successResult = userRepository.fetchUser(username).last()
        assertThat(successResult is Result.Success)

        // when failure scenario
        Mockito.reset(userRemoteDataSource)

        `when`(userRemoteDataSource.getUser(username))
            .thenReturn(Result.Error(Exception()))

        val loadingResultOnError = userRepository.fetchUser(username).first()
        assertThat(loadingResultOnError is Result.Loading)

        val failureResult = userRepository.fetchUser(username).last()
        assertThat(failureResult is Result.Success)
    }

    @Test
    fun fetchReposLocal() = runTest {
        val user = DumpData.getUser()
        val id = user.id
        val page = 0

        `when`(userLocalDataSource.getRepositories(id))
            .thenReturn(Result.Success(mutableListOf()))

        val loadingResult = userRepository.fetchRepos(user, page, false).first()
        assertThat(loadingResult is Result.Loading)

        val successResult = userRepository.fetchRepos(user, page, false).last()
        assertThat(successResult is Result.Success)

        // when failure scenario
        `when`(userLocalDataSource.getRepositories(id))
            .thenReturn(Result.Error(Exception()))

        val loadingResultOnError = userRepository.fetchRepos(user, page, false).first()
        assertThat(loadingResultOnError is Result.Loading)

        val failureResult = userRepository.fetchRepos(user, page, false).last()
        assertThat(failureResult is Result.Error)
    }

    @Test
    fun fetchReposRemote() = runTest {
        val user = DumpData.getUser()
        val username = "username"
        val page = 0

        `when`(userRemoteDataSource.getRepositories(username, page))
            .thenReturn(Result.Success(mutableListOf()))

        val loadingResult = userRepository.fetchRepos(user, page, false).first()
        assertThat(loadingResult is Result.Loading)

        val successResult = userRepository.fetchRepos(user, page, false).last()
        assertThat(successResult is Result.Success)

        // when failure scenario
        `when`(userRemoteDataSource.getRepositories(username, page))
            .thenReturn(Result.Error(Exception()))

        val loadingResultOnError = userRepository.fetchRepos(user, page, false).first()
        assertThat(loadingResultOnError is Result.Loading)

        val failureResult = userRepository.fetchRepos(user, page, false).last()
        assertThat(failureResult is Result.Error)
    }
}