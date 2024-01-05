package com.denisrx.cs2probet.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://hltv-leaderboard-fetcher-830d3fdbc2b1.herokuapp.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
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
