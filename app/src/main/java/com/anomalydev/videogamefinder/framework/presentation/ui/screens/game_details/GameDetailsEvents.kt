package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details

sealed class GameDetailsEvents {

    data class GetGameEvent(
        val id: Int,
    ): GameDetailsEvents()
}