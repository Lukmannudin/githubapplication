package com.lukmannudin.githubapp.data.user

import com.lukmannudin.githubapp.data.BaseResponse
import com.lukmannudin.githubapp.data.repo.remote.RepoRemote
import com.lukmannudin.githubapp.data.user.remote.UserRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface UserApiService {

    @GET("search/users")
    suspend fun search(@Query("q") searchWord: String, @Query("page") page: Int):
            Response<BaseResponse<List<UserRemote>>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserRemote>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<List<RepoRemote>>

    @GET
    suspend fun getUserByUrl(@Url url: String): Response<UserRemote>

}