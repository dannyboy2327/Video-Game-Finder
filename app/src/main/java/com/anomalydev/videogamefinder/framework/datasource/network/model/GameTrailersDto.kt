package com.anomalydev.videogamefinder.framework.datasource.network.model

import com.anomalydev.videogamefinder.business.domain.model.Trailer
import com.google.gson.annotations.SerializedName

data class GameTrailersDto(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("preview")
    var preview: String,

    @SerializedName("data")
    var trailer: Trailer
)