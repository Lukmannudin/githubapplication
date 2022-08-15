package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.mapper.NullableInputListMapper
import com.lukmannudin.githubapp.data.user.local.RepoLocal
import com.lukmannudin.githubapp.data.user.remote.RepoRemote

class UserLocalRepositoriesToUserRepositories(
    private val mapper: Mapper<RepoLocal, Repo>
) : NullableInputListMapper<RepoLocal, Repo> {
    override fun map(input: List<RepoLocal>?): List<Repo> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}