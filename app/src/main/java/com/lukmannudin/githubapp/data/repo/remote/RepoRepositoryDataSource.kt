package com.lukmannudin.githubapp.data.repo.remote

import com.lukmannudin.githubapp.data.Result

interface RepoRepositoryDataSource {
    suspend fun fetchRepos(userName: String): Result<List<List<RepoRemote>>>
}