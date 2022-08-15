package com.lukmannudin.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.githubapp.common.UiState
import com.lukmannudin.githubapp.common.extension.postFailureState
import com.lukmannudin.githubapp.common.extension.postLoadingState
import com.lukmannudin.githubapp.common.extension.postSuccessState
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<UiState<List<User>>>()
    val viewState: LiveData<UiState<List<User>>> = _viewState

    var currentPage: Int = 0
    var isOnScrollingPage: Boolean = false

    fun search(searchWord: String) {
        _viewState.postLoadingState()
        viewModelScope.launch {
            val users = userRepositoryImpl.search(searchWord, currentPage)
            users.collectLatest { userResponse ->
                when (userResponse) {
                    is Result.Loading -> {
                        _viewState.postLoadingState()
                    }
                    is Result.Error -> {
                        _viewState.postFailureState(userResponse.exception)
                    }
                    is Result.Success -> {
                        _viewState.postSuccessState(userResponse.data)
                        currentPage++
                    }
                }
            }
            currentPage++
        }
    }

    sealed class MainViewState {
        object Loading : MainViewState()
        data class UsersLoaded(val users: List<User>) : MainViewState()
        object UserLoadFailure : MainViewState()
    }
}