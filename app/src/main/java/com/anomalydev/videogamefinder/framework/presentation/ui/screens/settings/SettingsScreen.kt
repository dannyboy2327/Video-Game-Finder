package com.anomalydev.videogamefinder.framework.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.NightMode

@Composable
fun Settings() {
    VideoGameFinderTheme(
        darkTheme = false,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Settings")
                    },
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    NightMode()
                }

                Divider(
                    thickness = 1.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettings() {
    Settings()
}