package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.interactors.game.GetGame
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.util.GameDetailsViewModelConstants
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_details.util.GameDetailsViewModelConstants.GAME_ID_KEY
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.GameListEvents
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGame: GetGame,
    @Named("auth_key") private val key: String,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val game: MutableState<Game?> = mutableStateOf(null)

    val loading: MutableState<Boolean> = mutableStateOf(false)

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(GAME_ID_KEY)?.let { gameId ->
            onTriggerEvent(GameDetailsEvents.GetGameEvent(gameId))
        }
    }

    fun onTriggerEvent(event: GameDetailsEvents) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GameDetailsEvents.GetGameEvent -> {
                        if (game.value == null) {
                            getGame(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: $e, ${e.cause}")
                e.printStackTrace()
            }
        }
    }


    private fun getGame(id: Int) {
        getGame.execute(
            gameId = id,
            key = key,
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { data ->
                game.value = data
                savedStateHandle.set(GAME_ID_KEY, data.id)
            }

            dataState.error?.let { error ->
                Log.e(TAG, "getGame: $error")
                // TODO("Handle the error")
            }

        }.launchIn(viewModelScope)
    }
}