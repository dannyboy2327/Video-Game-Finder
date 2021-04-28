package com.anomalydev.videogamefinder.cache

import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity

class AppDatabaseFake {

    val games = mutableListOf<GameEntity>()
}