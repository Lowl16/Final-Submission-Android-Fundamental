package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.GitHubUserDetailResponse
import com.dicoding.githubuser.data.response.GitHubUserResponse
import com.dicoding.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ) : Call<GitHubUserResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ) : Call<GitHubUserDetailResponse>

    @GET("users/{username}/followers")
    fun userFollowers(
        @Path("username") username: String
    ) : Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun userFollowing(
        @Path("username") username: String
    ) : Call<List<ItemsItem>>
}