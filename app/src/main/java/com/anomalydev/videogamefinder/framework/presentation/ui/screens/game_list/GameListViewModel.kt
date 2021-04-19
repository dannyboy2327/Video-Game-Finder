package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val gameService: GameService,
    private val dtoMapper: GameDtoMapper,
    @Named("auth_key") private val key: String,
): ViewModel() {

    val games: MutableState<List<Game>> = mutableStateOf(ArrayList())

    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        onTriggerEvent(GameListEvents.SearchGamesEvent)
    }


    /**
     *  This function will trigger an event for GameListScreen
     */
    private fun onTriggerEvent(event: GameListEvents) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GameListEvents.SearchGamesEvent -> {
                        newGameSearch()
                    }

                    is GameListEvents.SearchNextPageEvent -> {
                        newSearchPage()
                    }

                    is GameListEvents.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: $e, ${e.cause}")
            }
        }
    }

    // 1st Use Case
    private suspend fun newGameSearch() {
        loading.value = true
        val result = gameService.searchGames(
            key = key,
            page = 1,
            pageSize = Constants.PAGE_SIZE,
            searchQuery = "Sonic"
        )
        games.value = dtoMapper.dtoToModelList(result.games)
        loading.value = false
    }

    // 2nd Use Case
    private fun newSearchPage() {

    }

    // 3rd Use Case
    private fun restoreState() {

    }

}