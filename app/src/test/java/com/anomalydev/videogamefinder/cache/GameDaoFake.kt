package com.anomalydev.videogamefinder.cache

import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity

class GameDaoFake(
    private val appDatabaseFake: AppDatabaseFake
): GameDao {

    override suspend fun insertGame(game: GameEntity): Long {
        appDatabaseFake.games.add(game)
        return 1 // return success
    }

    override suspend fun insertGames(games: List<GameEntity>): LongArray {
        appDatabaseFake.games.addAll(games)
        return longArrayOf(1) // return success
    }

    override suspend fun getGameById(id: Int): GameEntity? {
        return appDatabaseFake.games.find { it.id == id }
    }

    override suspend fun deleteGames(ids: List<Int>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllGames() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGame(primaryKey: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun searchGames(query: String, page: Int, pageSize: Int): List<GameEntity> {
        return appDatabaseFake.games
    }

    override suspend fun getAllGames(page: Int, pageSize: Int): List<GameEntity> {
        return appDatabaseFake.games
    }

    override suspend fun restoreGames(query: String, page: Int, pageSize: Int): List<GameEntity> {
        return appDatabaseFake.games
    }

    override suspend fun restoreAllGames(page: Int, pageSize: Int): List<GameEntity> {
        return appDatabaseFake.games
    }
}