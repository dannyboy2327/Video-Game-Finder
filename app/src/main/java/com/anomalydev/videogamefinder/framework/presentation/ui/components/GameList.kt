package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.util.Constants

@Composable
fun GameList(
    games: List<Game>,
    loading: Boolean,
    page: Int,
    onScrollPositionChanged: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
) {
    Box {
        if (games.isEmpty()) {

        } else {
            LazyColumn {
                itemsIndexed(
                    items = games,
                ) { index, game ->

                    onScrollPositionChanged(index)

                    if ((index + 1) >= (page * Constants.PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }

                    GameCard(
                        game = game
                    ) {
                        // On click navigate
                    }
                }
            }
        }
        CircularLoadingBar(isDisplayed = loading)
    }
}