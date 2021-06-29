package com.anomalydev.videogamefinder.framework.presentation.ui.navigation

sealed class Screen(
    val route: String
) {

    object Splash: Screen("splash")

    object GameList: Screen("gameList")

    object GameDetails: Screen("gameDetails")

    object GameTrailers: Screen("gameTrailers")

    object Settings: Screen("settings")
}