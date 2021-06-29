package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_trailers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.model.GameTrailers
import com.anomalydev.videogamefinder.business.interactors.game.GetGame
import com.anomalydev.videogamefinder.business.interactors.game_trailers.GetGameTrailers
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.GameDetailsEvents
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.util.GameDetailsViewModelConstants
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_trailers.util.GameTrailersViewModelConstants
import com.anomalydev.videogamefinder.framework.presentation.ui.util.DialogQueue
import com.anomalydev.videogamefinder.framework.presentation.util.ConnectivityManager
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameTrailersViewModel @Inject constructor(
    private val getGameTrailers: GetGameTrailers,
    private val connectivityManager: ConnectivityManager,
    @Named("auth_key") private val key: String,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val gameTrailers: MutableState<List<GameTrailers>> = mutableStateOf(ArrayList())

    val loading: MutableState<Boolean> = mutableStateOf(false)

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    init {
        savedStateHandle.get<Int>(GameTrailersViewModelConstants.GAME_ID_KEY)?.let { gameId ->
            onTriggerEvent(GameTrailersEvents.GetGameTrailersEvent(gameId))
        }
    }

    fun onTriggerEvent(event: GameTrailersEvents) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GameTrailersEvents.GetGameTrailersEvent -> {
                        getTrailers(event.id)
                    }
                }
            } catch (e: Exception) {
                Log.e(Constants.TAG, "onTriggerEvent: $e, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    // 1st case
    private fun getTrailers(id: Int) {
        Log.d(TAG, "getTrailers: $id")
        getGameTrailers.execute(
            gameId = id,
            key = key,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { data ->
                gameTrailers.value = data
                savedStateHandle.set(GameTrailersViewModelConstants.GAME_ID_KEY, id)
            }

            dataState.error?.let { error ->
                Log.e(Constants.TAG, "getTrailers: $error")
                dialogQueue.appendErrorMessage(
                    title = "Error",
                    description = error
                )
            }
        }.launchIn(viewModelScope)
    }
}