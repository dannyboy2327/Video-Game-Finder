package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view.GameView
import com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view.GameViewLoading
import com.anomalydev.videogamefinder.util.Constants.TAG

@Composable
fun GameDetailsScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: GameDetailsViewModel,
    gameId: Int?,
    onWebsiteClick: (String) -> Unit,
    onShareClick: (String, String) -> Unit,
    ) {
    if (gameId == null) {
        //TODO("Show Invalid Recipe")
    } else {

        val onLoad = viewModel.onLoad.value

        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(GameDetailsEvents.GetGameEvent(gameId))
        }

        val loading = viewModel.loading.value

        val game = viewModel.game.value

        val dialogQueue = viewModel.dialogQueue

        VideoGameFinderTheme(
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            dialogQueue = dialogQueue.queue.value
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (loading && game == null) {
                    GameViewLoading()
                } else if (!loading && game == null && onLoad) {
                    //TODO("Show Invalid Recipe")
                } else {
                    game?.let { game ->
                        GameView(
                            game = game,
                            onTriggerFavorite = {
                                viewModel.onTriggerEvent(GameDetailsEvents.BookmarkStateEvent(it))
                            },
                            onWebsiteClick = onWebsiteClick,
                            onShareClick = onShareClick,
                        )
                    }
                }
            }
        }
    }
}