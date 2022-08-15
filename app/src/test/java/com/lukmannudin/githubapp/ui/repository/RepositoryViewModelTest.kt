package com.lukmannudin.githubapp.ui.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.lukmannudin.githubapp.DumpData
import com.lukmannudin.githubapp.FakeUserRepository
import com.lukmannudin.githubapp.MainCoroutineRule
import com.lukmannudin.githubapp.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryViewModelTest {
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var fakeUserRepository: FakeUserRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        fakeUserRepository = FakeUserRepository()
        viewModel = RepositoryViewModel(fakeUserRepository, testDispatcher)
    }

    @Test
    fun getSuccessRepositories() = runTest {
        viewModel.initData(DumpData.getUser())
        viewModel.getRepositories()
        assertThat(viewModel.viewState.value is UiState.Success).isTrue()
    }

    @Test
    fun getFailureRepositories() = runTest {
        viewModel.getRepositories()
        assertThat(viewModel.viewState.value is UiState.Failure).isTrue()
    }
}