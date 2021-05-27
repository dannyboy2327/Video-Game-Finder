package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.interactors.game_list.GetFavoriteGames
import com.anomalydev.videogamefinder.business.interactors.game_list.RestoreGames
import com.anomalydev.videogamefinder.business.interactors.game_list.SearchGames
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list.util.GameListViewModelConstants
import com.anomalydev.videogamefinder.framework.presentation.ui.util.DialogQueue
import com.anomalydev.videogamefinder.util.Constants.PAGE_SIZE
import com.anomalydev.videogamefinder.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val searchGames: SearchGames,
    private val getFavoriteGames: GetFavoriteGames,
    private val restoreGames: RestoreGames,
    @Named("auth_key") private val key: String,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    // Used to receive the list of games
    val games: MutableState<List<Game>> = mutableStateOf(ArrayList())

    // Used to receive the list of favorite games
    val favoriteGames: MutableState<List<Game>> = mutableStateOf(listOf())

    // Used to set loading state of the app
    val loading: MutableState<Boolean> = mutableStateOf(false)

    // Used to set loading state of favorite games
    val loadingFavoriteGames: MutableState<Boolean> = mutableStateOf(false)

    // Used to search for new query or restore query
    val query: MutableState<String> = mutableStateOf("")

    // Used to append new list of games or restore page num
    val page: MutableState<Int> = mutableStateOf(1)

    // Used to restore gameListPosition num
    var gameListScrollPosition = 0

    val dialogQueue = DialogQueue()

    init {

        // Will restore the page num if not null
        savedStateHandle.get<Int>(GameListViewModelConstants.PAGE_KEY)?.let { page ->
            setPageNum(page)
        }

        // Will restore the query if not null
        savedStateHandle.get<String>(GameListViewModelConstants.QUERY_KEY)?.let { query ->
            setQuery(query)
        }

        // Will restore the scroll position if not null
        savedStateHandle.get<Int>(GameListViewModelConstants.SCROLL_POSITION)?.let { position ->
            setNewPosition(position)
        }

        // If position restored is not 0, scroll to that restored position
        if (gameListScrollPosition != 0) {
            onTriggerEvent(GameListEvents.RestoreStateEvent)
        } else {
            onTriggerEvent(GameListEvents.SearchGamesEvent)
            onTriggerEvent(GameListEvents.GetFavoriteGames)
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

                    is GameListEvents.GetFavoriteGames -> {
                        getFavoriteGames()
                    }
                    else -> { }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: $e, ${e.cause}")
            }
        }
    }

    // 1st Use Case
    private fun newGameSearch() {

        // Reset state for new search
        resetSearchState()

        searchGames.execute(
            key = key,
            page = page.value,
            pageSize = PAGE_SIZE,
            query = query.value,
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { list ->
                games.value = list
            }

            dataState.error?.let { error ->
                Log.e(TAG, "newGameSearch: $error")
                dialogQueue.appendErrorMessage(
                    title = "Error",
                    description = error
                )
            }

        }.launchIn(viewModelScope)
    }

    // 2nd Use Case
    private fun nextSearchPage() {
        if (gameListScrollPosition + 1 >= (page.value * PAGE_SIZE)) {

            incrementPageNum()

            if (page.value > 1) {
                searchGames.execute(
                    key = key,
                    page = page.value,
                    pageSize = PAGE_SIZE,
                    query = query.value,
                ).onEach { dataState ->

                    loading.value = dataState.loading

                    dataState.data?.let { list ->
                        appendListOfGames(list)
                    }

                    dataState.error?.let { error ->
                        Log.e(TAG, "nextSearchPage: $error")
                        dialogQueue.appendErrorMessage(
                            title = "Error",
                            description = error
                        )
                    }

                }.launchIn(viewModelScope)
            }
        }
    }

    // 3rd Use Case
    private fun restoreState() {
        restoreGames.execute(
            page = page.value,
            query = query.value,
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { list ->
                games.value = list
            }

            dataState.error?.let { error ->
                Log.e(TAG, "restoreState: $error")
                dialogQueue.appendErrorMessage(
                    title = "Error",
                    description = error
                )
            }

        }.launchIn(viewModelScope)
    }

    // 4th use case
    private fun getFavoriteGames(){
        getFavoriteGames.execute().onEach { dataState ->

            loadingFavoriteGames.value = dataState.loading

            dataState.data?.let { list ->
                Log.d(TAG, "getFavoriteGames: ${list.size}")
                favoriteGames.value = list
            }

            dataState.error?.let { error ->
                Log.e(TAG, "getFavoriteGames: $error")
                dialogQueue.appendErrorMessage(
                    title = "Error",
                    description = error
                )
            }

        }.launchIn(viewModelScope)
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
        savedStateHandle.set(GameListViewModelConstants.QUERY_KEY, this.query.value)
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
        gameListScrollPosition = position
        Log.d(TAG, "setNewPosition: $gameListScrollPosition")
        savedStateHandle.set(GameListViewModelConstants.SCROLL_POSITION, gameListScrollPosition)
    }

    /**
     *  Responsible for incrementing page number
     */
    private fun incrementPageNum() {
        setPageNum(page.value + 1)
    }

    /**
     *  Will set the new page number
     */
    private fun setPageNum(pageNum: Int) {
        page.value = pageNum
        savedStateHandle.set(GameListViewModelConstants.PAGE_KEY, page.value)
    }

    /**
     *  Responsible for appending new list of games
     */
    private fun appendListOfGames(result: List<Game>) {
        val currentList = ArrayList(games.value)
        currentList.addAll(result)
        games.value = currentList
    }

    /**
     *  Will reset the search state
     */
    private fun resetSearchState() {
        games.value = listOf()
        page.value = 1
        onChangeGameListPosition(0)
    }

}