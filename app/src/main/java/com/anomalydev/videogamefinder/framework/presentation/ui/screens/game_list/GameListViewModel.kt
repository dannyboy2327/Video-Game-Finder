package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameListViewModel @Inject constructor(
    gameService: GameService,
    dtoMapper: GameDtoMapper,
    @Named("auth_key") key: String,

): ViewModel() {

    val games: MutableState<List<Game>> = mutableStateOf(ArrayList())

    init {
        viewModelScope.launch {
            val result = gameService.searchGames(
                key = key,
                page = 1,
                pageSize = Constants.PAGE_SIZE,
                searchQuery = "Sonic"
            )
            games.value = dtoMapper.dtoToModelList(result.games)
        }
    }
}