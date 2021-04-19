package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list

sealed class GameListEvents {

    object SearchGamesEvent: GameListEvents()

    object SearchNextPageEvent: GameListEvents()

    object RestoreStateEvent: GameListEvents()
}