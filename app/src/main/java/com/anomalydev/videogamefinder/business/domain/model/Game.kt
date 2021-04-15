package com.anomalydev.videogamefinder.business.domain.model

/**
 * Data class for domain model that will be used for UI
 */
data class Game(
    val id: Int,
    val name: String,
    val released: String,
    val imageUrl: String,
    val rating: Int,
    val rating_top: Int,
    val updated: String,
)
