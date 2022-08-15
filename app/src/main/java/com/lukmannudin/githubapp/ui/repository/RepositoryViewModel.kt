package com.lukmannudin.githubapp.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.githubapp.common.UiState
import com.lukmannudin.githubapp.common.extension.postFailureState
import com.lukmannudin.githubapp.common.extension.postLoadingState
import com.lukmannudin.githubapp.common.extension.postSuccessState
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _viewState = MutableLiveData<UiState<List<Repo>>>()
    val viewState: LiveData<UiState<List<Repo>>> = _viewState

    var currentPage: Int = 0
    var isOnScrollingPage: Boolean = false

    fun initData(user: User?) {
        _user.value = user
        user?.let {
            getRepositories(user)
        }
    }

    private fun getRepositories(user: User, forceReload: Boolean = false) {
        _viewState.postLoadingState()
        viewModelScope.launch(ioDispatcher) {
            val repositoriesFlow = userRepository.fetchRepos(user, currentPage, forceReload)
            repositoriesFlow.collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _viewState.postFailureState(result.exception)
                    }
                    is Result.Success -> {
                        _viewState.postSuccessState(result.data)
                    }
                    Result.Loading -> {
                        _viewState.postLoadingState()
                    }
                }
            }
            currentPage++
        }
    }

    fun getRepositories(forceReload: Boolean = false) {
        user.value?.let { user ->
            getRepositories(user, forceReload)
        } ?: kotlin.run {
            _viewState.postFailureState(NullPointerException())
        }
    }
}