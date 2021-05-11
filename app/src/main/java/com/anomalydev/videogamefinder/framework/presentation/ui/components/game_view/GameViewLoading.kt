package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameViewLoading() {
    val transition = rememberInfiniteTransition()
    val time = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = LinearEasing,
            )
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
            )
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "L",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.0..0.14 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "o",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.14..0.28 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "a",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.28..0.42 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "d",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.42..0.56 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "i",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.56..0.7 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "n",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.7..0.84 -> 32.sp
                    else -> 24.sp
                },
                modifier = Modifier
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = "g",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = when (time.value) {
                    in 0.84..0.98 -> 32.sp
                    else -> 24.sp
                },
            )
            Text(
                text = "...",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 24.sp,
            )
        }
    }
}