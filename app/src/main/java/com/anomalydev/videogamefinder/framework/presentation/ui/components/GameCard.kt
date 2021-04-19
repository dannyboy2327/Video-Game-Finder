package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.google.accompanist.coil.CoilImage

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {

            CoilImage(
                data = game.imageUrl,
                contentDescription = game.name,
                fadeIn = true,
                error = {
                    Image(painter = painterResource(id = R.drawable.empty_plate), contentDescription = "Image not viewable")
                },
                modifier = Modifier
                    .height(225.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            ) {
                Text(
                    text = game.name,
                    modifier = Modifier
                        .fillMaxWidth(0.70f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Rating: ",
                    modifier = Modifier
                        .align(CenterVertically),
                    style = MaterialTheme.typography.h6
                )

                Text(
                    text = game.rating.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(CenterVertically),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}