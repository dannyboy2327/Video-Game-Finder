package com.anomalydev.videogamefinder.framework.presentation.ui.components.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R


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
            fontFamily = FontFamily(Font(R.font.debug)),
            style = MaterialTheme.typography.h1,
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
                painter = painterResource(id = R.drawable.ic_android),
                contentDescription = "Company logo",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(50.dp)
                    .padding(
                        end = 8.dp,
                    )
            )
            Text(
                text = "AnomalyDev",
                style = MaterialTheme.typography.h3,
                fontFamily = FontFamily(Font(R.font.debug)),
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
