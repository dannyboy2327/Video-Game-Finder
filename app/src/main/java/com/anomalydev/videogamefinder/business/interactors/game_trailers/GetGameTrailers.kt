package com.anomalydev.videogamefinder.business.interactors.game_trailers

import android.util.Log
import com.anomalydev.videogamefinder.business.domain.model.GameTrailers
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameTrailerDtoMapper
import com.anomalydev.videogamefinder.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGameTrailers(
    private val gameService: GameService,
    private val dtoMapper: GameTrailerDtoMapper,
) {

    fun execute(
        gameId: Int,
        key: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<GameTrailers>>> = flow {
        try {

            emit(DataState.loading<List<GameTrailers>>())

            if (isNetworkAvailable) {
                val listOfTrailers = getGamesTrailersFromNetwork(key, gameId)
                Log.d(TAG, "execute: ${listOfTrailers.size}")

                if (listOfTrailers.isNotEmpty()) {
                    emit(DataState.success(listOfTrailers))
                } else {
                    throw Exception("Unable to get the trailers from the network.")
                }
            }
        } catch (e: Exception) {
            emit(DataState.error<List<GameTrailers>>(e.message?: "Unknown error"))
        }
    }

    /**
     * Helper function to get a games trailers from the network
     */
    private suspend fun getGamesTrailersFromNetwork(key: String, gameId: Int): List<GameTrailers> {
        return dtoMapper.dtoToModelList(
            gameService.getGameTrailers(
                gameId = gameId,
                key = key,
            ).gameTrailers
        )
    }
}