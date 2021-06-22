package com.anomalydev.videogamefinder.framework.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.GameDetailsScreen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.GameDetailsViewModel
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.GameListScreen
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.GameListViewModel
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.settings.Settings
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val isDark = mutableStateOf(true)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Checks dataStore for darkTheme boolean
        observeDataStore()

        setContent {
            // Used to remember nav state in the graph
            val navController = rememberNavController()

            // Composable to link navController to graph
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
            ) {
                composable("splash") {
                    SplashScreen(
                        isDarkTheme = isDark.value,
                        navController = navController,
                    )
                }

                composable("gameList") { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val gameListViewModel: GameListViewModel = viewModel(LocalViewModelStoreOwner.current!!, "GameListViewModel", factory)
                    GameListScreen(
                        isDarkTheme = isDark.value,
                        viewModel = gameListViewModel,
                        onNavigateToGameDetailScreen = navController::navigate,
                        onNavigateToSettingsScreen = navController::navigate,
                    )
                }

                composable(
                    "gameDetails/{gameId}",
                    arguments = listOf(navArgument("gameId") { type = NavType.IntType})
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val gameDetailsViewModel: GameDetailsViewModel = viewModel(LocalViewModelStoreOwner.current!!, "GameDetailsViewModel", factory)
                    GameDetailsScreen(
                        isDarkTheme = isDark.value,
                        viewModel = gameDetailsViewModel,
                        gameId = navBackStackEntry.arguments?.getInt("gameId"),
                    )
                }

                composable("settings") {
                    Settings(
                        isDarkTheme = isDark.value,
                        onToggleTheme = { onToggleTheme() }
                    )
                }
            }
        }
    }

    /**
     *  Responsible for changing dark theme preference value on trigger
     */
    private fun onToggleTheme() {
        CoroutineScope(Dispatchers.Main).launch{
            dataStore.edit { preferences ->
                val current = preferences[DARK_THEME_KEY]?: false
                preferences[DARK_THEME_KEY] = !current
            }
        }
    }

    /**
     *  Responsible for observing any changes when darkTheme value changes
     */
    private fun observeDataStore() {
        dataStore.data.onEach { preferences ->
            preferences[DARK_THEME_KEY]?.let { isDarkTheme ->
                isDark.value = isDarkTheme
            }
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
    }
}
