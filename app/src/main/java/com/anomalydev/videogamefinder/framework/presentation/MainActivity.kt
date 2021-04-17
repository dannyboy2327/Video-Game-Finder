package com.anomalydev.videogamefinder.framework.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.API_HOST
import com.anomalydev.videogamefinder.util.Constants.PAGE_SIZE
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("auth_key")
    lateinit var key: String

    @Inject
    lateinit var gameService: GameService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            getGames()
        }

        setContent {

        }
    }

    suspend fun getGames() {
        val gameResponse = gameService.searchGames(key, API_HOST, 1, PAGE_SIZE, "Grand Theft Auto")
        Log.d(TAG, "getGames: ${gameResponse.games[0].name}")
    }
}
