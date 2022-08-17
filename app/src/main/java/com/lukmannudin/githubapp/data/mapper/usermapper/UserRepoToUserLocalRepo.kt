package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.user.local.RepoLocal

class UserRepoToUserLocalRepo(
    private val userId: Int
) : Mapper<Repo, RepoLocal> {
    override fun map(input: Repo): RepoLocal {
        return RepoLocal(
            input.id,
            input.stargazersCount,
            input.language,
            input.subscribersUrl,
            input.releasesUrl,
            input.svnUrl,
            input.name,
            input.jsonMemberPrivate,
            input.description,
            input.createdAt?.time,
            input.fullName,
            userId,
            input.updateAt?.time,
            input.ownerName,
            input.ownerAvatarUrl
        )
    }
}