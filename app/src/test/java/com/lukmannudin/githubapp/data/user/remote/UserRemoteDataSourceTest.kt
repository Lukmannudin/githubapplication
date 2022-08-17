package com.lukmannudin.githubapp.data.user.remote

import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukmannudin.githubapp.common.Constant
import com.lukmannudin.githubapp.common.extension.toDate
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.Result
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.enqueueResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val userApiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory((CoroutineCallAdapterFactory()))
        .build()
        .create(UserApiService::class.java)

    private val userRemoteDataSource = UserRemoteDataSource(userApiService)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun successSearch() {
        mockWebServer.enqueueResponse("search-user.json", 200)

        runTest {
            val actual = userRemoteDataSource.search("ramon", 0)
            val expected = getExpectedSearchedUser()

            assertThat(actual is Result.Success).isTrue()
            assertEquals(expected, (actual as Result.Success).data[0])
        }
    }

    @Test
    fun failureSearch() {
        mockWebServer.enqueueResponse("search-user.json", 404)

        runTest {
            val actual = userRemoteDataSource.search("ramon", 0)
            assertThat(actual is Result.Error).isTrue()
        }
    }

    @Test
    fun successGetUser() {
        mockWebServer.enqueueResponse("user.json", 200)

        runTest {
            val actual = userRemoteDataSource.getUser("ramon")
            val expected = Result.Success(getExpectedUser())

            assertThat(actual is Result.Success).isTrue()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun failureGetUser() {
        mockWebServer.enqueueResponse("user.json", 404)

        runTest {
            val actual = userRemoteDataSource.getUser("ramon")
            assertThat(actual is Result.Error).isTrue()
        }
    }

    @Test
    fun successGetRepositories() {
        mockWebServer.enqueueResponse("repository.json", 200)

        runTest {
            val actual = userRemoteDataSource.getRepositories("ramon", 0)
            val expected = getExpectedRepository()

            assertThat(actual is Result.Success).isTrue()
            assertEquals(expected, (actual as Result.Success).data[0])
        }
    }

    @Test
    fun failureGetRepositories() {
        mockWebServer.enqueueResponse("repository.json", 404)

        runTest {
            val actual = userRemoteDataSource.getRepositories("ramon", 0)
            assertThat(actual is Result.Error).isTrue()
        }
    }

    private fun getExpectedSearchedUser(): User {
        return User(
            login = "ramon",
            id = 1380,
            nodeId = "MDQ6VXNlcjEzODA=",
            avatarUrl = "https://avatars.githubusercontent.com/u/1380?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/ramon",
            htmlUrl = "https://github.com/ramon",
            followersUrl = "https://api.github.com/users/ramon/followers",
            followingUrl = "https://api.github.com/users/ramon/following{/other_user}",
            gistsUrl = "https://api.github.com/users/ramon/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/ramon/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/ramon/subscriptions",
            organizationsUrl = "https://api.github.com/users/ramon/orgs",
            reposUrl = "https://api.github.com/users/ramon/repos",
            eventsUrl = "https://api.github.com/users/ramon/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/ramon/received_events",
            type = "User",
            siteAdmin = false,
            following = -1,
            followers = -1,
            company = "",
            twitterUsername = "",
            location = "",
            email = ""
        )
    }

    private fun getExpectedUser(): User {
        return User(
            login = "ramon",
            id = 1380,
            nodeId = "MDQ6VXNlcjEzODA=",
            avatarUrl = "https://avatars.githubusercontent.com/u/1380?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/ramon",
            htmlUrl = "https://github.com/ramon",
            followersUrl = "https://api.github.com/users/ramon/followers",
            followingUrl = "https://api.github.com/users/ramon/following{/other_user}",
            gistsUrl = "https://api.github.com/users/ramon/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/ramon/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/ramon/subscriptions",
            organizationsUrl = "https://api.github.com/users/ramon/orgs",
            reposUrl = "https://api.github.com/users/ramon/repos",
            eventsUrl = "https://api.github.com/users/ramon/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/ramon/received_events",
            type = "User",
            siteAdmin = false,
            following = 27,
            followers = 42,
            company = "",
            twitterUsername = "",
            location = "Salvador, Bahia, Brazil",
            email = "ramongsoares@gmail.com"
        )
    }

    private fun getExpectedRepository(): Repo {
        return Repo(
            id = 280556683,
            stargazersCount = 0,
            language = "",
            subscribersUrl = "https://api.github.com/repos/ramon/Awesome-Profile-README-templates/subscribers",
            releasesUrl = "https://api.github.com/repos/ramon/Awesome-Profile-README-templates/releases{/id}",
            svnUrl = "https://github.com/ramon/Awesome-Profile-README-templates",
            name = "Awesome-Profile-README-templates",
            jsonMemberPrivate = false,
            description = "A collection of awesome readme templates to display on your profile",
            createdAt = "2020-07-18T01:13:58Z".toDate(Constant.CREATED_AT_PATTTERN),
            fullName = "ramon/Awesome-Profile-README-templates"
        )
    }
}