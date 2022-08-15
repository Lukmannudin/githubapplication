package com.lukmannudin.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> = _viewState

    var currentPage: Int = 0

    fun search(searchWord: String) {
        viewModelScope.launch {
            val users = userRepositoryImpl.search(searchWord, currentPage)
            users.collect { userResponse ->
                when (userResponse) {
                    is Result.Loading -> {
                        _viewState.value = MainViewState.Loading
                    }
                    is Result.Error -> {
                        _viewState.value = MainViewState.UserLoadFailure
                    }
                    is Result.Success -> {
                        _viewState.value = MainViewState.UsersLoaded(userResponse.data)
                        currentPage++
                    }
                }
            }
        }
        //todo paging users
    }

    sealed class MainViewState {
        object Loading : MainViewState()
        data class UsersLoaded(val users: List<User>) : MainViewState()
        object UserLoadFailure : MainViewState()
    }
}