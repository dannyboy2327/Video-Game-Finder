package com.anomalydev.videogamefinder.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class GameDto(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("released")
    var released: String,

    @SerializedName("background_image")
    var image: String,

    @SerializedName("rating")
    var rating: Float,

    @SerializedName("rating_top")
    var ratingTop: Int,

    @SerializedName("updated")
    var updated: String
) {
}