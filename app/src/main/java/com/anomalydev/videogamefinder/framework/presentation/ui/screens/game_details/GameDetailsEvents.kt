package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details

import com.anomalydev.videogamefinder.business.domain.model.Game

sealed class GameDetailsEvents {

    data class GetGameEvent(
        val id: Int,
    ): GameDetailsEvents()

    data class BookmarkStateEvent(
        val game: Game,
    ): GameDetailsEvents()
}