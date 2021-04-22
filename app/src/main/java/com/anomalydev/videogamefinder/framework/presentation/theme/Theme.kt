package com.anomalydev.videogamefinder.framework.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = black900,
    primaryVariant = black700,
    onPrimary = Color.White,
    secondary = white50,
    secondaryVariant = whiteGrey200,
    onSecondary = Color.Black,
    error = redErrorDark,
    onError = redErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = black1,
    onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = red600,
    primaryVariant = red300,
    onPrimary = black1,
    secondary = white50,
    secondaryVariant = whiteGrey200,
    onSecondary = Color.Black,
    error = redErrorDark,
    onError = redErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = black2,
)

@Composable
fun VideoGameFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}