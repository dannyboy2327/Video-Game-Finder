package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
) {

    val painter = rememberCoilPainter(
        request = game.background_image,
        fadeIn = true,
    )

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 8.dp,
                end = 6.dp,
            )
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = game.name,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(BottomStart)
                    .padding(
                        start = 6.dp,
                        bottom = 6.dp,
                        end = 6.dp,
                    ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}