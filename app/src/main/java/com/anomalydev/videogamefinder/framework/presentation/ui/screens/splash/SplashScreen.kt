package com.anomalydev.videogamefinder.framework.presentation.ui.screens.splash


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
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
    isNetworkAvailable: Boolean,
    navController: NavController,
) {

    VideoGameFinderTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
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