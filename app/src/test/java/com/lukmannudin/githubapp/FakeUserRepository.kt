package com.lukmannudin.githubapp

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.data.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private var isShouldReturnError: Boolean = false

    override suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>> {
        return flow {
            if (isShouldReturnError) {
                emit(Result.Error(Exception()))
            } else {
                emit(Result.Success(mutableListOf()))
            }
        }
    }

    override suspend fun fetchUser(username: String): Flow<Result<User>> {
        return flow {
            if (isShouldReturnError) {
                emit(Result.Error(Exception()))
            } else {
                emit(Result.Success(
                    DumpData.getUser()
                ))
            }
        }
    }

    override suspend fun fetchRepos(
        user: User,
        page: Int,
        forceReload: Boolean
    ): Flow<Result<List<Repo>>> {
        return flow {
            if (isShouldReturnError) {
                emit(Result.Error(Exception()))
            } else {
                emit(Result.Success(mutableListOf()))
            }
        }
    }

    fun setReturnError(value: Boolean) {
        isShouldReturnError = value
    }
}