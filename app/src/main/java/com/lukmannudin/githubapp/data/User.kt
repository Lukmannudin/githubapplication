package com.lukmannudin.githubapp.data

data class User(
    val gistsUrl: String,
    val reposUrl: String,
    val followingUrl: String,
    val starredUrl: String,
    val login: String,
    val followersUrl: String,
    val type: String,
    val url: String,
    val subscriptionsUrl: String,
    val score: Double,
    val receivedEventsUrl: String,
    val avatarUrl: String,
    val eventsUrl: String,
    val htmlUrl: String,
    val siteAdmin: Boolean,
    val id: Int,
    val gravatarId: String,
    val nodeId: String,
    val organizationsUrl: String,
    val following: Int,
    val followers: Int,
    val company: String,
    val twitterUsername: String,
    val location: String,
    val email: String
)