package com.anomalydev.videogamefinder.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.GameDetailsScreen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.GameListScreen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.GameListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Used to remember nav state in the graph
            val navController = rememberNavController()

            // Composable to link navController to graph
            NavHost(
                navController = navController,
                startDestination = Screen.GameList.route,
            ) {
                composable("gameList") { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val gameListViewModel: GameListViewModel = viewModel("GameListViewModel", factory)
                    GameListScreen(
                        viewModel = gameListViewModel,
                    )
                }

                composable("gameDetails") {
                    GameDetailsScreen()
                }
            }
        }
    }
}
