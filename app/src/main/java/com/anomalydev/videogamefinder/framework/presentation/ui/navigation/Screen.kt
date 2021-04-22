package com.anomalydev.videogamefinder.framework.presentation.ui.navigation

sealed class Screen(
    val route: String
) {

    object GameList: Screen("gameList")

    object GameDetails: Screen("gameDetails")

    object Settings: Screen("settings")
}