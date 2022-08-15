package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>>

    suspend fun fetchUser(username: String): Flow<Result<User>>

    suspend fun fetchRepos(user: User, page: Int, forceReload: Boolean): Flow<Result<List<Repo>>>

}