package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.presentation.ui.components.GameImageBody

@Composable
fun GameView(
    game: Game,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {
        GameImageHeading(
            game = game,
        )

        GameImageBody(
            game = game,
        )

        Divider(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                ),
            thickness = 1.dp,
            color = MaterialTheme.colors.onBackground,
        )

        Text(
            text = "Website",
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .wrapContentWidth()
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                ),
            style = MaterialTheme.typography.h5,
        )

        GameViewWebsite(
            website = game.website,
        )

        Divider(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                ),
            thickness = 1.dp,
            color = MaterialTheme.colors.onBackground,
        )

        Text(
            text = "Reviews",
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .wrapContentWidth()
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                ),
            style = MaterialTheme.typography.h5,
        )

        for (rating in game.ratings) {
            GameViewReview(
                rating = rating,
            )
        }
    }
}