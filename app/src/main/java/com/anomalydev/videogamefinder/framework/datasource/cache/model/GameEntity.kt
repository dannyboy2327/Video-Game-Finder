package com.anomalydev.videogamefinder.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anomalydev.videogamefinder.business.domain.model.Rating

@Entity(tableName = "games")
data class GameEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "updated")
    var updated: String,

    @ColumnInfo(name = "background_image")
    var background_image: String,

    @ColumnInfo(name = "website")
    var website: String,

    @ColumnInfo(name = "rating")
    var rating: Float,

    @ColumnInfo(name = "rating_top")
    var rating_top: Int,

    @ColumnInfo(name = "ratings")
    var ratings: List<Rating>,

    @ColumnInfo(name = "playtime")
    var playtime: Int,
)