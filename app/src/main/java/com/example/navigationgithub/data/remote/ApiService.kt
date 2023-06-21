package com.example.navigationgithub.data.remote

import com.example.navigationgithub.data.response.DetailUserResponse
import com.example.navigationgithub.data.response.SearchUser
import com.example.navigationgithub.data.response.SearchUserResponse
import retrofit2.Call

import retrofit2.http.*

interface ApiService {
    // TODO:  Add Github API Token inside the authorization like this (Authorization: token YOUR_GITHUB_API_TOKEN_HERE)
    @GET("search/users")
    @Headers("Authorization: ") //
    fun getUser(
        @Query("q") username: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ")
    fun getDetailUser(
        @Path("username") username: String
    ):Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: ")
    fun getFollowing(
        @Path("username") username: String
    ):Call<List<SearchUser>>

    @GET("users/{username}/followers")
    @Headers("Authorization: ")
    fun getFollower(
        @Path("username") username: String
    ):Call<List<SearchUser>>
}
