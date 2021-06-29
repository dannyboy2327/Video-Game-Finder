package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_trailers

import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.GameDetailsEvents

sealed class GameTrailersEvents {

    data class GetGameTrailersEvent(
        val id: Int,
    ): GameTrailersEvents()
}