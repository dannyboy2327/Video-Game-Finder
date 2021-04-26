package com.anomalydev.videogamefinder.framework.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Black900,
    primaryVariant = Black700,
    onPrimary = White50,
    secondary = White50,
    secondaryVariant = White200,
    onSecondary = Black900,
    error = redErrorDark,
    onError = redErrorLight,
    background = Grey900,
    onBackground = Grey600,
    surface = Grey900,
    onSurface = Grey600,
)

private val LightColorPalette = lightColors(
    primary = Black500,
    primaryVariant = Black300,
    onPrimary = White50,
    secondary = White50,
    secondaryVariant = White200,
    onSecondary = Black900,
    error = redErrorDark,
    onError = redErrorLight,
    background = Grey700,
    onBackground = Grey400,
    surface = Grey700,
    onSurface = Grey400,
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
        typography = CodaTypography,
        shapes = Shapes,
        content = content
    )
}