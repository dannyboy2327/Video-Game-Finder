package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.anomalydev.videogamefinder.business.domain.model.Game

@Composable
fun GameList(
    games: List<Game>
) {
    LazyColumn {
        itemsIndexed(
            items = games,
        ) { index, game ->

            GameCard(
                game = game
            ) {
                // On click navigate
            }
        }
    }
}