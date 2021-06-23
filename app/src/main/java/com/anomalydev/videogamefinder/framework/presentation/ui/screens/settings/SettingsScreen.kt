package com.anomalydev.videogamefinder.framework.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anomalydev.videogamefinder.framework.presentation.theme.Coda
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.NightMode

@Composable
fun Settings(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
) {
    VideoGameFinderTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.h4,
                        )
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
                    NightMode(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { onToggleTheme() }
                    )
                }

                Divider(
                    thickness = 1.dp
                )
            }
        }
    }
}