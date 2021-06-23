package com.anomalydev.videogamefinder.framework.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.anomalydev.videogamefinder.framework.presentation.ui.components.ConnectivityMonitor
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.GenericDialog
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.GenericDialogInfo
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.NegativeAction
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.PositiveAction
import com.anomalydev.videogamefinder.framework.presentation.ui.util.DialogQueue
import java.util.*

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
    isNetworkAvailable: Boolean,
    dialogQueue: Queue<GenericDialogInfo>? = null,
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
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = if (!darkTheme) Grey700 else Grey900)
        ) {
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }
            dialogQueue?.let {
                ProcessDialogQueue(dialogQueue = it)
            }
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>
) {
    dialogQueue.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction,
        )
    }
}