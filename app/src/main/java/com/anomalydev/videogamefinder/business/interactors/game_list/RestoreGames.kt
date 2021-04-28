package com.anomalydev.videogamefinder.business.interactors.game_list

import android.util.Log
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.util.Constants.PAGE_SIZE
import com.anomalydev.videogamefinder.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RestoreGames(
    private val gameDao: GameDao,
    private val entityMapper: GameEntityMapper,
){
    fun execute(
        page: Int,
        query: String,
    ): Flow<DataState<List<Game>>> = flow {
        try {

            emit(DataState.loading<List<Game>>())

            val cacheResult = if (query.isBlank()) {
                gameDao.restoreAllGames(
                    pageSize = PAGE_SIZE,
                    page = page,
                )
            } else {
                gameDao.restoreGames(
                    query = query,
                    page = page,
                    pageSize = PAGE_SIZE,
                )
            }

            val list = entityMapper.toModelList(cacheResult)
            emit(DataState.success(list))

        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<List<Game>>(e.message?: "Unknown"))
        }
    }
}