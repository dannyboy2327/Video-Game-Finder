package com.anomalydev.videogamefinder.framework.presentation.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.splash.SplashScreenContent
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    isDarkTheme: Boolean,
    navController: NavController,
) {

    VideoGameFinderTheme(
        darkTheme = isDarkTheme,
    ) {
        SplashScreenContent()
        LaunchedEffect(
            key1 = "",
            block = {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(3000)
                    navController.popBackStack()
                    navController.navigate(Screen.GameList.route)
                }
            }
        )
    }
}