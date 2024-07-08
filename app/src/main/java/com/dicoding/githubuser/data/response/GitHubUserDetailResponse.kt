package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class GitHubUserDetailResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("html_url")
	val htmlUrl: String
)
