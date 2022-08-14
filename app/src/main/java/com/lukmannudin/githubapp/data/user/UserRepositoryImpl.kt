package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.user.remote.UserRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userRemoteDataSource: UserDataSource
) : UserRepository {

    override suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.search(searchWord, page)) {
            is Result.Success -> {
                val users = mutableListOf<User>()
                users.addAll(resultRemote.data)
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
            Result.Loading -> emit(Result.Loading)
        }
    }

    override suspend fun fetchUser(username: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.getUser(username)) {
            is Result.Success -> {
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
            Result.Loading -> emit(Result.Loading)
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

}