package com.lukmannudin.githubapp.data.user.remote

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.usermapper.UserMapper
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import com.lukmannudin.githubapp.data.user.UserApiService
import com.lukmannudin.githubapp.data.user.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApiService: UserApiService
) : UserDataSource {
    override suspend fun search(searchWord: String, page: Int): Result<List<User>> {
        val remoteUsers: MutableList<UserRemote> = mutableListOf()
        try {
            val response = userApiService.search(searchWord, page)

            response.body()?.items?.let { items ->
                remoteUsers.addAll(items)
            }

            if (!response.isSuccessful) {
                return Result.Error(Exception("Get data users from server failed"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }

        if (remoteUsers.isNotEmpty()) {
            remoteUsers.addAll(listOf())
        }

        return Result.Success(mergeSearchApiWithUser(remoteUsers))
    }

    private suspend fun mergeSearchApiWithUser(users: List<UserRemote>): List<User> {
        val mergedUsers = mutableListOf<User>()

        coroutineScope {
            (users.indices).map { index ->
                val userRemote = users[index]
                async(Dispatchers.IO) {
                    userRemote.login?.let { username ->
                        val result = getUser(username)
                        if (result is Result.Success) {
                            mergedUsers.add(result.data)
                        }
                    }
                }
            }.awaitAll()
        }

        return mergedUsers
    }

    override suspend fun getUser(username: String): Result<User> {
        return try {
            val response = userApiService.getUser(username)
            val user = UserMapper.userRemoteToUser(response.body()!!)
            Result.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }
    }

    override suspend fun getRepos(username: String): Result<List<Repo>> {
        val repoRemote: MutableList<RepoRemote> = mutableListOf()
        try {
            val response = userApiService.getRepos(username)
            repoRemote.addAll(response.body()!!)
            if (!response.isSuccessful) {
                return Result.Error(Exception("Get data users from server failed"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }

        if (repoRemote.isNotEmpty()) {
            repoRemote.addAll(listOf())
        }

        return Result.Success(
            UserMapper.userRemoteReposToUserRepos(repoRemote)
        )
    }


}