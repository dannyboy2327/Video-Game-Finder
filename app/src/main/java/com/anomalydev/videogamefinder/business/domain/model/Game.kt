package com.anomalydev.videogamefinder.business.domain.model

/**
 * Data class for domain model that will be used for UI
 */
data class Game(
    val id: Int,
    val name: String,
    val description: String,
    val released: String,
    val updated: String,
    val background_image: String,
    val website: String,
    val rating: Float,
    val rating_top: Int,
    val ratings: List<Rating>,
    val playtime: Int,
    val isFavorite: Boolean,
)

data class Rating(
    val id: Int,
    val title: String,
    val count: Int,
    val percent: Float,
)
