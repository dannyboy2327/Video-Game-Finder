package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun LoadingGameListShimmer(
    cardHeight: Dp,
    cardWidth: Dp,
) {

    BoxWithConstraints {
        val cardWidthPx = with(LocalDensity.current) { cardWidth.toPx() }
        val cardHeightPx = with(LocalDensity.current) { cardHeight.toPx() }
        val gradientWidth: Float = (0.1f * cardHeightPx)

        val infiniteTransition = rememberInfiniteTransition()
        val xCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWidthPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )
        val yCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = .9f),
            Color.LightGray.copy(alpha = .3f),
            Color.LightGray.copy(alpha = .9f),
        )

        LazyRow {
            items(4) {
                ShimmerGameCardItem(
                    colors = colors,
                    cardHeight = cardHeight,
                    cardWidth = cardWidth,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    gradientWidth = gradientWidth,
                )
            }
        }
    }
}