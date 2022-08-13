package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.mapper.NullableInputListMapper
import com.lukmannudin.githubapp.data.user.remote.UserRemote

class UsersRemoteToUsers(
    private val mapper: Mapper<UserRemote, User>
) : NullableInputListMapper<UserRemote, User> {
    override fun map(input: List<UserRemote>?): List<User> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}