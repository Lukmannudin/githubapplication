package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.common.extension.onFailure
import com.lukmannudin.githubapp.common.extension.onSuccess
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

        userRemoteDataSource.search(searchWord, page).apply {
            onFailure { exception ->
                emit(Result.Error(exception))
            }
            onSuccess { users ->
                val mergedUsers = mergeSearchWithGetUser(users)
                saveUsersToDatabase(mergedUsers)

                emit(Result.Success(mergedUsers))
            }
        }
    }

    override suspend fun fetchUser(username: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        userLocalDataSource.getUser(username).apply {
            onFailure {
                userRemoteDataSource.getUser(username).apply {
                    onSuccess { user ->
                        saveUser(user)
                        emit(Result.Success(user))
                    }
                    onFailure { exception ->
                        emit(Result.Error(exception))
                    }
                }
            }

            onSuccess { user ->
                emit(Result.Success(user))
            }
        }
    }

    override suspend fun fetchRepos(username: String): Flow<Result<List<Repo>>> = flow {
        emit(Result.Loading)
        userRemoteDataSource.getRepos(username).apply {
            onSuccess { repositories ->
                emit(Result.Success(repositories))
            }
            onFailure { exception ->
                emit(Result.Error(exception))
            }
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