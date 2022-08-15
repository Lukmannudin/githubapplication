package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.mapper.NullableInputListMapper
import com.lukmannudin.githubapp.data.user.local.RepoLocal
import com.lukmannudin.githubapp.data.user.remote.RepoRemote

class UserRepositoriesToUserLocalRepositories(
    private val mapper: Mapper<Repo, RepoLocal>
) : NullableInputListMapper<Repo, RepoLocal> {
    override fun map(input: List<Repo>?): List<RepoLocal> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}