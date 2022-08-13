package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>>

    suspend fun fetchUser(username: String): Flow<Result<User>>

    suspend fun fetchRepos(username: String): Flow<Result<List<Repo>>>

}