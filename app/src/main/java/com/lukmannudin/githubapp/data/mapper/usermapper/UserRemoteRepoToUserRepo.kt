package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.common.extension.toDate
import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote

class UserRemoteRepoToUserRepo : Mapper<RepoRemote, Repo> {
    override fun map(input: RepoRemote): Repo {
        return Repo(
            input.stargazersCount ?: 0,
            input.language ?: "",
            input.subscribersUrl ?: "",
            input.releasesUrl ?: "",
            input.svnUrl ?: "",
            input.id ?: -1,
            input.name ?: "",
            input.jsonMemberPrivate ?: false,
            input.description ?: "",
            input.createdAt?.toDate("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            input.fullName ?: ""
        )
    }
}