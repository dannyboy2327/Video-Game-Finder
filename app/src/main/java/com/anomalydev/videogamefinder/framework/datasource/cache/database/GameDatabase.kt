package com.anomalydev.videogamefinder.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity

@Database(entities = [GameEntity::class], version = 2)
abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {

        const val DATABASE_NAME = "game_db"
    }
}