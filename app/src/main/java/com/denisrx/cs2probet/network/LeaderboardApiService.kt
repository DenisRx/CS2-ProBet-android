package com.denisrx.cs2probet.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://hltv-leaderboard-fetcher-830d3fdbc2b1.herokuapp.com"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object LeaderboardApi{
    val retrofitService : LeaderboardApiService by lazy {
        retrofit.create(LeaderboardApiService::class.java)
    }
}

interface LeaderboardApiService {
    @GET("leaderboard")
    suspend fun getLeaderboard(): List<ApiTeam>
}
