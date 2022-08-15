package com.lukmannudin.githubapp.data.user.local

import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.usermapper.UserMapper

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
}