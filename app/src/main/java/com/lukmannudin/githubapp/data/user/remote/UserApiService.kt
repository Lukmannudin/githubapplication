package com.lukmannudin.githubapp.data.user.remote

import com.lukmannudin.githubapp.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("search/users")
    suspend fun search(@Query("q") searchWord: String, @Query("page") page: Int):
            Response<BaseResponse<List<UserRemote>>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserRemote>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String, @Query("page") page: Int): Response<List<RepoRemote>>

}