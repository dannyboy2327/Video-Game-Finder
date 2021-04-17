package com.anomalydev.videogamefinder.framework.datasource.network.response

import com.anomalydev.videogamefinder.framework.datasource.network.model.GameDto
import com.google.gson.annotations.SerializedName

data class GameSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("next")
    var next: String,

    @SerializedName("previous")
    var previous: String,

    @SerializedName("results")
    var games: List<GameDto>
) {
}