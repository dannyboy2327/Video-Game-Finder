package com.anomalydev.videogamefinder.business.interactors.game_list

import android.util.Log
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteGames(
    private val gameDao: GameDao,
    private val entityMapper: GameEntityMapper,
) {

    fun execute(): Flow<DataState<List<Game>>> = flow {

        try {
            // Emit loading state
            emit(DataState.loading<List<Game>>())

            // Get favorite games from cache
            val games = getFavoriteGamesFromCache()
            Log.d(TAG, "execute: ${games.size}")

            // Emit the list of  favorite games
            emit(DataState.success(games))

        } catch (e: Exception){
            // Emit error message
            emit(DataState.error<List<Game>>(e.message?: "Unknown error"))
        }
    }

    /**
     *  Get favorite games from the cache
     */
    private suspend fun getFavoriteGamesFromCache(): List<Game> {
        return entityMapper.toModelList(gameDao.getFavoriteGames())
    }
}