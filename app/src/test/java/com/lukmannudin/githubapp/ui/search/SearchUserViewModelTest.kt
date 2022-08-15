package com.lukmannudin.githubapp.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
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
class SearchUserViewModelTest {

    private lateinit var viewModel: SearchUserViewModel
    private lateinit var fakeUserRepository: FakeUserRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setupViewModel() {
        fakeUserRepository = FakeUserRepository()
        viewModel = SearchUserViewModel(fakeUserRepository, testDispatcher)
    }

    @Test
    fun successSearch(): Unit = runTest {
        viewModel.search("username")

        assertThat(viewModel.viewState.value is UiState.Success).isTrue()
    }

    @Test
    fun failSearch(): Unit = runTest {
        fakeUserRepository.setReturnError(true)
        viewModel.search("username")

        assertThat(viewModel.viewState.value is UiState.Failure).isTrue()
    }
}