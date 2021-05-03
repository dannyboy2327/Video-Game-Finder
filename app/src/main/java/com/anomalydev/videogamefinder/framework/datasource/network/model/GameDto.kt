package com.anomalydev.videogamefinder.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class GameDto(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String?,

    @SerializedName("released")
    var released: String?,

    @SerializedName("updated")
    var updated: String,

    @SerializedName("background_image")
    var background_image: String?,

    @SerializedName("website")
    var website: String?,

    @SerializedName("rating")
    var rating: Float,

    @SerializedName("rating_top")
    var rating_top: Int,

    @SerializedName("playtime")
    var playtime: Int,
) {
}