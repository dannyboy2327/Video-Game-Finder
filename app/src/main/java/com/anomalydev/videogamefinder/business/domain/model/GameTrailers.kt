package com.anomalydev.videogamefinder.business.domain.model

data class GameTrailers(
    val id: Int,
    val name: String,
    val preview: String,
    val trailer: Trailer
)

data class Trailer(
    val low: String,
    val max: String,
)
