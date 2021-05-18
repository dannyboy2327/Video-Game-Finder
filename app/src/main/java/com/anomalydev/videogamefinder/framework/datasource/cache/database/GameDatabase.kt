package com.anomalydev.videogamefinder.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity
import com.anomalydev.videogamefinder.framework.datasource.cache.util.Converter

@Database(entities = [GameEntity::class], version = 2)
@TypeConverters(Converter::class)
abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {

        const val DATABASE_NAME = "game_db"
    }
}