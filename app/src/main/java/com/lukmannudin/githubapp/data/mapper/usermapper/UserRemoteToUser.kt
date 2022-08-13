package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.user.remote.UserRemote

class UserRemoteToUser : Mapper<UserRemote, User> {
    override fun map(input: UserRemote): User {
        val following = input.following ?: -1
        val followers = input.followers ?: -1

        return User(
            input.gistsUrl!!,
            input.reposUrl!!,
            input.followingUrl!!,
            input.starredUrl!!,
            input.login!!,
            input.followersUrl!!,
            input.type!!,
            input.url!!,
            input.subscriptionsUrl!!,
            -1.0,
            input.receivedEventsUrl!!,
            input.avatarUrl!!,
            input.eventsUrl!!,
            input.htmlUrl!!,
            input.siteAdmin!!,
            input.id!!,
            input.gravatarId!!,
            input.nodeId!!,
            input.organizationsUrl!!,
            following,
            followers,
            input.company!!,
            input.twitterUsername!!,
            input.location!!,
            input.email!!
        )
    }
}