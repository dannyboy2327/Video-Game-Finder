package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.model.GameDto
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val gameService: GameService,
    private val dtoMapper: GameDtoMapper,
    @Named("auth_key") private val key: String,
): ViewModel() {

    // Used to receive the list of games
    val games: MutableState<List<Game>> = mutableStateOf(ArrayList())

    // Used to set loading state of the app
    val loading: MutableState<Boolean> = mutableStateOf(false)

    // Used to search for new query or restore query
    val query: MutableState<String> = mutableStateOf("")

    // Used to append new list of games or restore page num
    val page: MutableState<Int> = mutableStateOf(1)

    // Used to restore gameListPosition num
    var gameListScrollPosition = 0

    init {
        // If position restored is not 0, scroll to that restored position
        if (gameListScrollPosition != 0) {

        } else {
            onTriggerEvent(GameListEvents.SearchGamesEvent)
        }
    }


    /**
     *  This function will trigger an event for GameListScreen
     */
    fun onTriggerEvent(event: GameListEvents) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GameListEvents.SearchGamesEvent -> {
                        newGameSearch()
                    }

                    is GameListEvents.SearchNextPageEvent -> {
                        nextSearchPage()
                    }

                    is GameListEvents.RestoreStateEvent -> {
                        restoreState()
                    }
                    else -> { }
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
            page = page.value,
            pageSize = Constants.PAGE_SIZE,
            searchQuery = query.value,
        )
        games.value = dtoMapper.dtoToModelList(result.games)
        loading.value = false
    }

    // 2nd Use Case
    private suspend fun nextSearchPage() {
        if (gameListScrollPosition + 1 >= (page.value * Constants.PAGE_SIZE)) {

            incrementPageNum()

            if (page.value > 1) {

                loading.value =  true

                val result = gameService.searchGames(
                    key = key,
                    page = page.value,
                    pageSize = Constants.PAGE_SIZE,
                    searchQuery = query.value,
                )

                appendListOfGames(dtoMapper.dtoToModelList(result.games))

                loading.value = false
            }
        }
    }

    // 3rd Use Case
    private fun restoreState() {

    }

    /**
     *  Responsible for calling function to set new query
     */
    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    /**
     *  Will set the new query value
     */
    private fun setQuery(query: String) {
        this.query.value = query
    }

    /**
     *  Responsible for calling function to set new position
     */
    fun onChangeGameListPosition(position: Int) {
        setNewPosition(position)
    }

    /**
     *  Will set the new scroll position
     */
    private fun setNewPosition(position: Int) {
        this.gameListScrollPosition = position
        Log.d(TAG, "setNewPosition: $gameListScrollPosition")
    }

    /**
     *  Responsible for incrementing page number
     */
    fun incrementPageNum() {
        setPageNum(page.value + 1)
    }

    /**
     *  Will set the new page number
     */
    private fun setPageNum(pageNum: Int) {
        this.page.value = pageNum
    }

    /**
     *  Responsible for appending new list of games
     */
    private fun appendListOfGames(result: List<Game>) {
        val currentList = ArrayList(this.games.value)
        currentList.addAll(result)
        this.games.value = currentList
    }

}