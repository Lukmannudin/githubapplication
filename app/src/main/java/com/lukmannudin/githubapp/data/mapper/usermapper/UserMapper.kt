package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import com.lukmannudin.githubapp.data.user.local.UserLocal
import com.lukmannudin.githubapp.data.user.remote.UserRemote

object UserMapper {

    fun userRemoteToUser(userRemote: UserRemote): User {
        return UserRemoteToUser().map(userRemote)
    }

    fun usersRemoteToUsers(usersRemote: List<UserRemote>): List<User> {
        return UsersRemoteToUsers(UserRemoteToUser()).map(usersRemote)
    }

    fun userRemoteReposToUserRepos(repoRemote: List<RepoRemote>): List<Repo> {
        return UserRemoteReposToUserRepos(UserRemoteRepoToUserRepo()).map(repoRemote)
    }

    fun userLocalToUser(userLocal: UserLocal): User {
        return UserLocalToUser().map(userLocal)
    }

    fun userToLocalUser(user: User): UserLocal {
        return UserToUserLocal().map(user)
    }
}