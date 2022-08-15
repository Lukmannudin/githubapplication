package com.lukmannudin.githubapp.data.mapper.usermapper

import com.lukmannudin.githubapp.common.extension.toDate
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.mapper.Mapper
import com.lukmannudin.githubapp.data.user.local.RepoLocal
import com.lukmannudin.githubapp.data.user.remote.RepoRemote

class UserLocalRepoToUserRepo: Mapper<RepoLocal, Repo> {
    override fun map(input: RepoLocal): Repo {
        return Repo(
            input.stargazersCount,
            input.language,
            input.subscribersUrl,
            input.releasesUrl,
            input.svnUrl,
            input.id,
            input.name,
            input.jsonMemberPrivate,
            input.description,
            input.createdAt?.toDate(),
            input.fullName
        )
    }
}