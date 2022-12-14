package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.mapper.NullableInputListMapper
import com.lukmannudin.githubapp.data.user.remote.RepoRemote

class UserRemoteReposToUserRepos(
    private val mapper: Mapper<RepoRemote, Repo>
) : NullableInputListMapper<RepoRemote, Repo> {
    override fun map(input: List<RepoRemote>?): List<Repo> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}