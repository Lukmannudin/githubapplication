package com.lukmannudin.githubapp.data.repo

import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import kotlinx.coroutines.flow.Flow

interface RepoRepositoryContract {
    suspend fun fetchRepos(username: String): Flow<Result<List<RepoRemote>>>
}