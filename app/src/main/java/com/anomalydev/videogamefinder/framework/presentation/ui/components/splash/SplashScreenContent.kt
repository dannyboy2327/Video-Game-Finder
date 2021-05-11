package com.anomalydev.videogamefinder.framework.presentation.ui.components.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
            ),
    ) {
        Text(
            text = "Video Game Finder",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "Company Logo",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        end = 8.dp,
                    )
            )
            Text(
                text = "AnomalyDev",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenContentPreview() {
    SplashScreenContent()
}
