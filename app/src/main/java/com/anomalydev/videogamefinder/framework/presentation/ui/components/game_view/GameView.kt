package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.business.domain.model.Game

@Composable
fun GameView(
    game: Game,
    onTriggerFavorite: (Game) -> Unit,
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
            onTriggerFavorite = onTriggerFavorite
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
                    bottom = 8.dp,
                ),
            style = MaterialTheme.typography.h5,
        )

        for (i in game.ratings.indices) {
            val rating = game.ratings[i]
            GameViewReview(
                title = rating.title,
                percentValue = rating.percent,
                percentValueMax = 100f,
                ratingCount = rating.count,
                animationDelay = i * 100,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}