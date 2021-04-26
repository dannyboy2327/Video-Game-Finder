package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerGameCardItem(
    colors: List<Color>,
    cardHeight: Dp,
    cardWidth: Dp,
    xShimmer: Float,
    yShimmer: Float,
    gradientWidth: Float
) {
    val brush = linearGradient(
        colors,
        start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
        end = Offset(xShimmer, yShimmer),
    )

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 8.dp,
                end = 6.dp,
            )
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}