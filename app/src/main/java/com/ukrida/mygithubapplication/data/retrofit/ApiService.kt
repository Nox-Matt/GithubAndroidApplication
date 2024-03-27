package com.ukrida.mygithubapplication.data.retrofit

import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.respond.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>
    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ) : Call<DetailResponse>
    @GET("users/{username}/followers")
    fun getUsersFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): Call<List<DetailResponse>>
    @GET("users/{username}/following")
    fun getUsersFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): Call<List<DetailResponse>>
}