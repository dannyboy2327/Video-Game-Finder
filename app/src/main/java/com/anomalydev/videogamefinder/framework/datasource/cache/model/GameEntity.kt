package com.anomalydev.videogamefinder.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "rating")
    var rating: Float,

    @ColumnInfo(name = "rating_top")
    var rating_top: Int,

    @ColumnInfo(name = "updated")
    var updated: String,
)