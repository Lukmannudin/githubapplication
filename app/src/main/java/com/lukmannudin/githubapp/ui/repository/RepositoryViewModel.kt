package com.lukmannudin.githubapp.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _repositories = MutableLiveData<RepositoryViewState>()
    val repositories: LiveData<RepositoryViewState> = _repositories

    fun initData(user: User?) {
        _user.value = user
        user?.let {
            getRepositories(user)
        }
    }

    fun getRepositories(user: User) {
        _repositories.value = RepositoryViewState.Loading
        viewModelScope.launch {
            val repositoryFlow = userRepository.fetchRepos(user.login)
            repositoryFlow.collect { result ->
                when (result) {
                    is Result.Error -> {
                        _repositories.postValue(RepositoryViewState.RepositoryFailure)
                    }
                    is Result.Success -> {
                        _repositories.postValue(RepositoryViewState.RepositoryLoaded(result.data))
                    }
                    Result.Loading -> {
                        _repositories.postValue(RepositoryViewState.Loading)
                    }
                }
            }
        }
    }

    sealed class RepositoryViewState {
        object Loading : RepositoryViewState()
        data class RepositoryLoaded(val repositories: List<Repo>) : RepositoryViewState()
        object RepositoryFailure : RepositoryViewState()
    }
}