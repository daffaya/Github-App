package com.example.navigationgithub.Api

import com.example.navigationgithub.Response.DetailUserResponse
import com.example.navigationgithub.Response.SearchUser
import com.example.navigationgithub.Response.SearchUserResponse
import retrofit2.Call

import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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") //
    fun getUser(
        @Query("q") username: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ):Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ):Call<List<SearchUser>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(
        @Path("username") username: String
    ):Call<List<SearchUser>>
}
