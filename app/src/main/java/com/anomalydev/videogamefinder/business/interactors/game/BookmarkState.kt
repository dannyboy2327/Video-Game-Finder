package com.anomalydev.videogamefinder.business.interactors.game

import android.util.Log
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.state.DataState
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class BookmarkState(
    private val gameDao: GameDao,
    private val entityMapper: GameEntityMapper,
) {

    fun execute(
        game: Game,
    ): Flow<DataState<Game>> = flow {

        try {
            val updatedGame = setBookmarkState(game)

            gameDao.updateGame(
                entityMapper.mapFromDomainModel(updatedGame)
            )

            if (updatedGame.isFavorite != game.isFavorite) {
                Log.d(TAG, "execute: Updated game: ${updatedGame.isFavorite} and ${game.isFavorite}")
                emit(DataState.success(updatedGame))
            } else {
                throw Exception("Updated game and old game have same isFavorite state")
            }
        } catch (e: Exception){
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<Game>(e.message?: "Unknown"))
        }
    }

    private fun setBookmarkState(game: Game): Game {
        return Game(
            id = game.id,
            name = game.name,
            description = game.description,
            released = game.released,
            updated = game.updated,
            background_image = game.background_image,
            website = game.website,
            rating = game.rating,
            rating_top = game.rating_top,
            ratings = game.ratings,
            playtime = game.playtime,
            isFavorite = !game.isFavorite,
        )
    }
}