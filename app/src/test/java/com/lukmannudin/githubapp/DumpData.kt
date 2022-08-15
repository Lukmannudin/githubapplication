package com.lukmannudin.githubapp

import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.data.user.remote.UserRemote

object DumpData {
    fun getUser(): User {
        return User(
            12,
            "gistUrl",
            "reposUrl",
            "followingUrl",
            "starredUrl",
            "login",
            "followersUrl",
            "type",
            "url",
            "subscriptionsUrl",
            "receivedEventUrl",
            "avatarUrl",
            "eventUrl",
            "htmlUrl",
            false,
            "gravatarId",
            "nodeId",
            "organizationsUrl",
            145,
            12,
            "company",
            "twitterUserName",
            "location",
            "email"
        )
    }

    fun getUserRemote(): UserRemote {
        return UserRemote(
            "gistUrl",
            "reposUrl",
            "followingUrl",
            "twitterUserName",
            "bio",
            "createdAt",
            "login",
            "type",
            "blog",
            "subscriptionUrl",
            "updateAt",
            false,
            "eventUrl",
            12,
            5,
            "gravatarId",
            "email",
            "nodeId",
            false,
            "starredUrl",
            "followersUrl",
            12,
            "company",
            "location"
        )
    }
}