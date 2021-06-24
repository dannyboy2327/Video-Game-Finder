package com.anomalydev.videogamefinder.business.interactors.game_list

import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SearchGames(
    private val gameDao: GameDao,
    private val gameService: GameService,
    private val entityMapper: GameEntityMapper,
    private val dtoMapper: GameDtoMapper,
){
    fun execute(
        key: String,
        page: Int,
        query: String,
        pageSize: Int,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Game>>> = flow {
        try {
            emit(DataState.loading<List<Game>>())

            if (isNetworkAvailable) {
                val games = getGamesFromNetwork(
                    key = key,
                    page = page,
                    query = query,
                    pageSize = pageSize
                )

                // Insert into the cache
                gameDao.insertGames(games = entityMapper.toEntityList(games))
            }

            // Query the cache
            val cacheResult = if (query.isBlank()) {
                gameDao.getAllGames(
                    page = page,
                    pageSize = pageSize,
                )
            } else {
                gameDao.searchGames(
                    query = query,
                    page = page,
                    pageSize = pageSize,
                )
            }

            // Emit list of games from cache
            val list = entityMapper.toModelList(cacheResult)
            emit(DataState.success(list))

        } catch (e: Exception) {
            emit(DataState.error<List<Game>>(e.message?: "Unknown error"))
        }
    }

    /**
     *  Get games from the network and map to the domain model
     *  !Can throw exception if no network connection!
     */
    private suspend fun getGamesFromNetwork(
        key: String,
        page: Int,
        query: String,
        pageSize: Int,
    ): List<Game> {
        return dtoMapper.dtoToModelList(
            gameService.searchGames(
                key = key,
                page = page,
                pageSize = pageSize,
                searchQuery = query,
            ).games
        )
    }
}