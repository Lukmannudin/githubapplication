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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _repositoriesState = MutableLiveData<UiState<List<Repo>>>()
    val repositoriesState: LiveData<UiState<List<Repo>>> = _repositoriesState

    var currentPage: Int = 0
    var isOnScrollingPage: Boolean = false

    fun initData(user: User?) {
        _user.value = user
        user?.let {
            getRepositories(user)
        }
    }

    private fun getRepositories(user: User, forceReload: Boolean = false) {
        _repositoriesState.postLoadingState()
        viewModelScope.launch {
            val repositoriesFlow = userRepository.fetchRepos(user, currentPage, forceReload)
            repositoriesFlow.collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _repositoriesState.postFailureState(result.exception)
                    }
                    is Result.Success -> {
                        _repositoriesState.postSuccessState(result.data)
                    }
                    Result.Loading -> {
                        _repositoriesState.postLoadingState()
                    }
                }
            }
            currentPage++
        }
    }

    fun getRepositories(forceReload: Boolean = false) {
        user.value?.let { user ->
            getRepositories(user, forceReload)
        }
    }
}