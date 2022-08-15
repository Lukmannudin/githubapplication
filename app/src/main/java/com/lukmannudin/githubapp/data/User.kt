package com.lukmannudin.githubapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val gistsUrl: String,
    val reposUrl: String,
    val followingUrl: String,
    val starredUrl: String,
    val login: String,
    val followersUrl: String,
    val type: String,
    val url: String,
    val subscriptionsUrl: String,
    val receivedEventsUrl: String,
    val avatarUrl: String,
    val eventsUrl: String,
    val htmlUrl: String,
    val siteAdmin: Boolean,
    val gravatarId: String,
    val nodeId: String,
    val organizationsUrl: String,
    val following: Int,
    val followers: Int,
    val company: String,
    val twitterUsername: String,
    val location: String,
    val email: String
) : Parcelable