package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.presentation.ui.components.GameList
import com.google.accompanist.coil.CoilImage


@Composable
fun GameListScreen(
    viewModel: GameListViewModel
) {

    val games = viewModel.games.value

    GameList(
        games = games,
    )
}
