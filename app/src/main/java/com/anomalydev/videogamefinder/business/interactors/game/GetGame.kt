package com.anomalydev.videogamefinder.business.interactors.game

import android.util.Log
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetGame(
    private val gameDao: GameDao,
    private val gameService: GameService,
    private val entityMapper: GameEntityMapper,
    private val dtoMapper: GameDtoMapper,
) {

    fun execute(
        gameId: Int,
        key: String,
    ): Flow<DataState<Game>> = flow {
        try {

            emit(DataState.loading<Game>())

            var game = getGameFromCache(gameId = gameId)

            if (game != null) {
                emit(DataState.success(game))
            } else {
//                val networkGame = getGameFromNetwork(key = key, gameId = gameId)
//                gameDao.insertGame(
//                   entityMapper.mapFromDomainModel(networkGame)
//                )
//
//                game = getGameFromCache(gameId)
//
//                if (game != null) {
//                    emit(DataState.success(game))
//                } else {
//                    throw Exception("Unable to get the game from the cache")
//                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<Game>(e.message?: "Unknown"))
        }
    }

    /**
     *  Helper function to get a game from the cache
     */
    private suspend fun getGameFromCache(gameId: Int): Game? {
        return gameDao.getGameById(gameId)?.let { gameEntity ->
            entityMapper.mapToDomainModel(gameEntity)
        }
    }

    /**
     * Helper function to get a game from the network
     * !Currently api has no method to obtain a game by itself!
     */
    private suspend fun getGameFromNetwork(key: String, gameId: Int): Game? {
        return null
    }

}