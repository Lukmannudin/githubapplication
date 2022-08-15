package com.lukmannudin.githubapp.data.user.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserLocal(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "gists_url")
    val gistsUrl: String,

    @ColumnInfo(name = "repos_url")
    val reposUrl: String,

    @ColumnInfo(name = "following_url")
    val followingUrl: String,

    @ColumnInfo(name = "starred_url")
    val starredUrl: String,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "followers_url")
    val followersUrl: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl: String,

    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "events_url")
    val eventsUrl: String,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "site_admin")
    val siteAdmin: Boolean,

    @ColumnInfo(name = "gravatar_id")
    val gravatarId: String,

    @ColumnInfo(name = "node_id")
    val nodeId: String,

    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "company")
    val company: String,

    @ColumnInfo(name = "twitter_username")
    val twitterUsername: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "email")
    val email: String
)