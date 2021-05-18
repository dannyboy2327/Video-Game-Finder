package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GameViewReview(
    title: String,
    percentValue: Float,
    percentValueMax: Float,
    ratingCount: Int,
    height: Dp = 24.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
){

    val animationPlayed = remember { mutableStateOf(false) }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed.value) {
            percentValue / percentValueMax
        } else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
        )
    )
    
    LaunchedEffect(key1 = true) {
        animationPlayed.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
            )
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(CircleShape)
                .background(MaterialTheme.colors.onBackground)
        ){
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(currentPercent.value)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(Color.Red)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = (currentPercent.value * percentValueMax).toInt().toString(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}