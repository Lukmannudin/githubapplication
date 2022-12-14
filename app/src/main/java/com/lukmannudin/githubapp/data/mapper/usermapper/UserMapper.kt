package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.data.user.local.RepoLocal
import com.lukmannudin.githubapp.data.user.remote.RepoRemote
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

    fun repositoriesLocalToRepositories(localRepositories: List<RepoLocal>): List<Repo> {
        return UserLocalRepositoriesToUserRepositories(UserLocalRepoToUserRepo()).map(localRepositories)
    }

    fun repositoriesToLocalRepositories(repositories: List<Repo>, userId: Int): List<RepoLocal> {
        return UserRepositoriesToUserLocalRepositories(UserRepoToUserLocalRepo(userId)).map(repositories)
    }

    fun repoLocalToRepo(repoLocal: RepoLocal): Repo {
        return UserLocalRepoToUserRepo().map(repoLocal)
    }

    fun repoToLocal(repo: Repo, userId: Int): RepoLocal {
        return UserRepoToUserLocalRepo(userId).map(repo)
    }
}