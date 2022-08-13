package com.lukmannudin.githubapp.data.repo

import com.lukmannudin.githubapp.data.Result
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import com.lukmannudin.githubapp.data.repo.remote.RepoRepositoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoRepository(
    private val repoRemoteDataSource: RepoRepositoryDataSource,
) : RepoRepositoryContract {

    override suspend fun fetchRepos(username: String): Flow<Result<List<RepoRemote>>> = flow {

    }
}