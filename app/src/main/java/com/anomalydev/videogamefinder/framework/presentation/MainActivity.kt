package com.anomalydev.videogamefinder.framework.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.anomalydev.videogamefinder.framework.presentation.ui.util.DialogQueue
import com.anomalydev.videogamefinder.framework.presentation.util.ConnectivityManager
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val isDark = mutableStateOf(true)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Checks dataStore for darkTheme boolean
        observeDataStore()

        setContent {
            // Used to check for internet availability
            val isInternetAvailable = connectivityManager.isNetworkAvailable.value
            Log.d(TAG, "onCreate: Is internet available? $isInternetAvailable")

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
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        navController = navController,
                    )
                }

                composable("gameList") { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val gameListViewModel: GameListViewModel = viewModel(
                        LocalViewModelStoreOwner.current!!,
                        "GameListViewModel", factory
                    )
                    GameListScreen(
                        isDarkTheme = isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
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
                    val gameDetailsViewModel: GameDetailsViewModel = viewModel(
                        LocalViewModelStoreOwner.current!!,
                        "GameDetailsViewModel", factory
                    )
                    GameDetailsScreen(
                        isDarkTheme = isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        viewModel = gameDetailsViewModel,
                        gameId = navBackStackEntry.arguments?.getInt("gameId"),
                        onWebsiteClick = { url ->
                            openGameWebsite(url)
                        },
                        onShareClick = { name, url ->
                            shareGame(name, url)
                        }
                    )
                }

                composable("settings") {
                    Settings(
                        isDarkTheme = isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onToggleTheme = { onToggleTheme() }
                    )
                }
            }
        }
    }

    /**
     * Responsible for starting an intent to share a url
     */
    private fun shareGame(name: String, url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Hey! Found a game that I'd like to share! It's called $name. " +
                        "Here's the link $url."
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    /**
     *  Responsible for starting an intent to open up a web url
     */
    private fun openGameWebsite(url: String) {
        val webPage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val dialogQueue = DialogQueue()
            dialogQueue.appendErrorMessage(
                title = e.cause.toString(),
                description = e.message.toString(),
            )
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
