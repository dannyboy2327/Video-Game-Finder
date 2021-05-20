package com.anomalydev.videogamefinder.framework.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.TAG

@Composable
fun FavoriteGameList(
    favoriteGames: List<Game>,
    onNavigateToGameDetailScreen: (String) -> Unit,
) {
    LazyRow {
        itemsIndexed(
            items = favoriteGames,
        ) { index, game ->
            GameCard(
                game = game,
                onClick = {
                    val route = Screen.GameDetails.route + "/${game.id}"
                    onNavigateToGameDetailScreen(route)
                }
            )
        }
    }
}