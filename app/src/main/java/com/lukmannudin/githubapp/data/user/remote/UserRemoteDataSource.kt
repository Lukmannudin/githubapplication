package com.lukmannudin.githubapp.data.user.remote

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.usermapper.UserMapper
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend fun search(searchWord: String, page: Int): Result<List<User>> {
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

        return Result.Success(UserMapper.usersRemoteToUsers(remoteUsers))
    }

    suspend fun getUser(username: String): Result<User> {
        return try {
            val response = userApiService.getUser(username)
            if (!response.isSuccessful) {
                return Result.Error(Exception(response.message()))
            }

            response.body()?.let { userRemote ->
                Result.Success(UserMapper.userRemoteToUser(userRemote))
            } ?: kotlin.run {
                Result.Error(Exception("something error on server"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }
    }

    suspend fun getRepos(username: String): Result<List<Repo>> {
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