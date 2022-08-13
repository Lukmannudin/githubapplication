package com.lukmannudin.githubapp.data.repo.remote

import com.google.gson.annotations.SerializedName

data class RepoRemote(

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("subscribers_url")
	val subscribersUrl: String? = null,

	@field:SerializedName("releases_url")
	val releasesUrl: String? = null,

	@field:SerializedName("svn_url")
	val svnUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("private")
	val jsonMemberPrivate: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,
)

