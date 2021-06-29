package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen

@Composable
fun GameImageBody(
    game: Game,
    onTriggerFavorite: (Game) -> Unit,
    onShareGame: (String, String) -> Unit,
    onNavigateToGameTrailersScreen: (String) -> Unit,
) {

    val expanded = remember { mutableStateOf(false) }

    Button(
        onClick = {
            val route = Screen.GameTrailers.route + "/${game.id}"
            onNavigateToGameTrailersScreen(route)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
            )

    ) {

        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play button",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.CenterVertically),
        )

        Text(
            text = "Play Trailers",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.button,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(
                    start = 6.dp,
                )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 16.dp,
            )
    ) {
        Text(
            text = game.description,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = if (expanded.value) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = if (expanded.value) "Close" else "See more",
            color = Color.Red,
            style = MaterialTheme.typography.button,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, MaterialTheme.colors.background),
                        0f,
                        100f,
                    )
                )
                .clickable {
                    expanded.value = !expanded.value
                }
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        Button(
            onClick = {
                onTriggerFavorite(game)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary)
        ) {
            Icon(
                imageVector = if (game.isFavorite) Icons.Outlined.BookmarkRemove else Icons.Filled.BookmarkAdd,
                contentDescription = if (game.isFavorite) "Remove" else "Favorite",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            )

            Text(
                text = if (game.isFavorite) "Remove" else "Favorite",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        start = 6.dp,
                    )
            )
        }

        Button(
            onClick = {
                onShareGame(game.name, game.website)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            )

            Text(
                text = "Share",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        start = 6.dp,
                    )
            )
        }
    }
}