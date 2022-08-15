package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.user.local.UserLocal
import com.lukmannudin.githubapp.data.user.remote.UserRemote

class UserToUserLocal : Mapper<User, UserLocal> {
    override fun map(input: User): UserLocal {
        return UserLocal(
            input.id,
            input.gistsUrl,
            input.reposUrl,
            input.followingUrl,
            input.starredUrl,
            input.login,
            input.followersUrl,
            input.type,
            input.url,
            input.subscriptionsUrl,
            input.receivedEventsUrl,
            input.avatarUrl,
            input.eventsUrl,
            input.htmlUrl,
            input.siteAdmin,
            input.gravatarId,
            input.nodeId,
            input.organizationsUrl,
            input.following,
            input.followers,
            input.company,
            input.twitterUsername,
            input.location,
            input.email
        )
    }
}