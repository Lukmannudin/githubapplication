package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.data.user.remote.UserRemote
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote

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
}