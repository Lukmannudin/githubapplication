package com.lukmannudin.githubapp.data.user.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class RepoLocal(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "subscribers_url")
    val subscribersUrl: String,

    @ColumnInfo(name = "releasesUrl")
    val releasesUrl: String,

    @ColumnInfo(name = "svn_url")
    val svnUrl: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "json_member_private")
    val jsonMemberPrivate: Boolean,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long?,

    @ColumnInfo(name = "fullName")
    val fullName: String,

    @ColumnInfo(name = "user_id")
    val userId: Int
)