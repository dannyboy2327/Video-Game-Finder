package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameViewWebsite(
    website: String,
    onWebsiteClick: (String) -> Unit,
) {
    Text(
        text = website,
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.body1,
        fontSize = 14.sp,
        modifier = Modifier
            .wrapContentWidth()
            .clickable(
                enabled = true,
                onClick = {
                    onWebsiteClick(website)
                }
            )
            .padding(
                top = 6.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    )
}