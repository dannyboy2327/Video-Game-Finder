package com.anomalydev.videogamefinder.framework.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.TAG

@Composable
fun GameList(
    games: List<Game>,
    loading: Boolean,
    page: Int,
    onScrollPositionChanged: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    onNavigateToGameDetailScreen: (String) -> Unit,
) {
    Box {
        if (games.isEmpty()) {

        } else {
            LazyRow {
                itemsIndexed(
                    items = games,
                ) { index, game ->

                    onScrollPositionChanged(index)

                    if ((index + 1) >= (page * Constants.PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }

                    GameCard(
                        game = game,
                        onClick = {
                            val route = Screen.GameDetails.route + "/${game.id}"
                            onNavigateToGameDetailScreen(route)
                            Log.d(TAG, "GameList: Game id: ${game.id}")
                        }
                    )
                }
            }
        }
        CircularLoadingBar(isDisplayed = loading)
    }
}