package com.lukmannudin.githubapp.data.user.local

import com.lukmannudin.githubapp.data.mapper.usermapper.UserMapper
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User

class UserLocalDataSource(
    private val userDao: UserDao
) {

    suspend fun getUser(username: String): Result<User> {
        return try {
            val userLocal = userDao.getUserByLogin(username)
            val user = UserMapper.userLocalToUser(userLocal)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun saveUser(user: User) {
        userDao.insert(UserMapper.userToLocalUser(user))
    }

    suspend fun getRepositories(userId: Int): Result<List<Repo>> {
        return try {
            val repositoryLocal = userDao.getRepositoryByUserId(userId)
            val repository = UserMapper.repositoriesLocalToRepositories(repositoryLocal)
            Result.Success(repository)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun saveRepository(repo: Repo, userId: Int) {
        userDao.insert(UserMapper.repoToLocal(repo, userId))
    }
}