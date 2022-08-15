package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.user.local.UserLocalDataSource
import com.lukmannudin.githubapp.data.user.remote.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.search(searchWord, page)) {
            is Result.Success -> {
                val mergedUsers = mergeSearchWithGetUser(resultRemote.data)
                saveUsersToDatabase(mergedUsers)

                emit(Result.Success(mergedUsers))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
            Result.Loading -> emit(Result.Loading)
        }
    }

    override suspend fun fetchUser(username: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val resultLocal = userLocalDataSource.getUser(username)
        if (resultLocal is Result.Success) {
            emit(Result.Success(resultLocal.data))
        } else {
            val resultRemote = userRemoteDataSource.getUser(username)
            if (resultRemote is Result.Success) {
                saveUser(resultRemote.data)
            }
            emit(resultRemote)
        }
    }

    override suspend fun fetchRepos(username: String): Flow<Result<List<Repo>>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.getRepos(username)) {
            is Result.Success -> {
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
            Result.Loading -> emit(Result.Loading)
        }
    }

    private suspend fun saveUsersToDatabase(users: List<User>) {
        users.forEach { user ->
            saveUser(user)
        }
    }

    private suspend fun saveUser(user: User) {
        userLocalDataSource.saveUser(user)
    }

    private suspend fun mergeSearchWithGetUser(users: List<User>): List<User> {
        val mergedUsers = mutableListOf<User>()
        users.forEach { userRemote ->
            userRemote.login.let { username ->
                val userRemoteResult = fetchUser(username)
                userRemoteResult.collectLatest { result ->
                    if (result is Result.Success) {
                        mergedUsers.add(result.data)
                    }
                }
            }
        }
        return mergedUsers
    }
}