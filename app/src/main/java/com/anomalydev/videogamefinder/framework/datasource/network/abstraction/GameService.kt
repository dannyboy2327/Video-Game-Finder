package com.anomalydev.videogamefinder.framework.datasource.network.abstraction

import com.anomalydev.videogamefinder.framework.datasource.network.response.GameSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GameService {

    @GET("games")
    suspend fun searchGames(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") searchQuery: String
    ): GameSearchResponse
}