package com.anomalydev.videogamefinder.framework.datasource.network.abstraction

import com.anomalydev.videogamefinder.framework.datasource.network.model.GameDto
import com.anomalydev.videogamefinder.framework.datasource.network.response.GameSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GameService {

    @GET("games")
    suspend fun searchGames(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
        @Query("page") page: Int,
        @Query("name") query: String
    ): GameSearchResponse

    @GET("games")
    suspend fun getGame(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
        @Query("id") id: Int
    ): GameDto
}