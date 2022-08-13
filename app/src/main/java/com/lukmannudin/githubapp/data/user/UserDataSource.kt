package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User

interface UserDataSource {
    suspend fun search(searchWord: String, page: Int): Result<List<User>>

    suspend fun getUser(username: String): Result<User>

    suspend fun getRepos(username: String): Result<List<Repo>>
}