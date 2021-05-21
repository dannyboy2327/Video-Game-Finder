package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anomalydev.videogamefinder.R
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.util.Constants
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun GameImageHeading(
    game: Game
) {

    var ratingValue: Float = rememberSaveable { game.rating }

    val painter = rememberCoilPainter(
        request = game.background_image,
        fadeIn = true,
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painter,
            contentDescription = game.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    bottom = 8.dp,
                    end = 6.dp,
                ),
        ) {
            Text(
                text = game.name,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h3,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 6.dp
                        )
                )

                Text(
                    text = "${game.playtime}hrs",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 6.dp
                        )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 6.dp
                        )
                )

                Text(
                    text = game.released,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 6.dp
                        )
                )
            }
        }
    }
}