package com.lukmannudin.githubapp.data.model

import java.util.*

data class Repo(
    val stargazersCount: Int,
    val language: String,
    val subscribersUrl: String,
    val releasesUrl: String,
    val svnUrl: String,
    val id: Int,
    val name: String,
    val jsonMemberPrivate: Boolean,
    val description: String,
    val createdAt: Date?,
    val fullName: String
)