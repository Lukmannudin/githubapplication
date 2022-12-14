package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.common.Constant
import com.lukmannudin.githubapp.common.extension.toDate
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.user.remote.RepoRemote

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
            input.createdAt?.toDate(Constant.CREATED_AT_PATTTERN),
            input.fullName ?: "",
            input.updateAt?.toDate(Constant.CREATED_AT_PATTTERN),
            input.owner?.login,
            input.owner?.avatarUrl
        )
    }
}