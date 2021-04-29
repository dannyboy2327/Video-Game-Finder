package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.google.accompanist.coil.CoilImage

@Composable
fun GameView(
    game: Game,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {
       game.imageUrl?.let { url ->
           CoilImage(
               data = url,
               contentDescription = game.name,
               fadeIn = true,
               error = {
                   Image(
                       painter = painterResource(id = R.drawable.empty_plate),
                       contentDescription = "Image not viewable"
                   )
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(260.dp),
               contentScale = ContentScale.Crop
           )
       }
    }
}