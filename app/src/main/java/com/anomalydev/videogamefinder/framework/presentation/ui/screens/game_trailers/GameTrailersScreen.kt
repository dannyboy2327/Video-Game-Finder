package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_trailers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.exo_player.Player
import com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view.GameViewLoading

@Composable
fun GameTrailersScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: GameTrailersViewModel,
    gameId: Int?,
) {
    if (gameId == null) {
        TODO("Show invalid gameId")
    } else {

        val onLoad = viewModel.onLoad.value

        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(GameTrailersEvents.GetGameTrailersEvent(gameId))
        }

        val loading = viewModel.loading.value

        val gameTrailers = viewModel.gameTrailers.value

        val dialogQueue = viewModel.dialogQueue

        VideoGameFinderTheme(
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            dialogQueue = dialogQueue.queue.value,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (loading && gameTrailers.isEmpty()) {
                    GameViewLoading()
                } else if (!loading && gameTrailers.isEmpty() && onLoad) {
                    //TODO("Show no trailers")
                } else {
                    Player(gameTrailers)
                }
            }
        }
    }
}