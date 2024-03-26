package com.ukrida.mygithubapplication.data.retrofit

import com.ukrida.mygithubapplication.data.respond.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>
}