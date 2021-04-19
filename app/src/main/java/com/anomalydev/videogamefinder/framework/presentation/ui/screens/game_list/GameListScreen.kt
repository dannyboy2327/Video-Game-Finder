package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list



import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.anomalydev.videogamefinder.framework.presentation.ui.components.GameList
import com.anomalydev.videogamefinder.framework.presentation.ui.components.SearchBar


@ExperimentalComposeUiApi
@Composable
fun GameListScreen(
    viewModel: GameListViewModel
) {

    val games = viewModel.games.value

    val loading = viewModel.loading.value

    val query = viewModel.query.value

    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChanged = viewModel::onQueryChanged,
                onExecuteSearch = {
                    viewModel.onTriggerEvent(GameListEvents.SearchGamesEvent)
                },
            )
        }
    ) {
        GameList(
            games = games,
            loading = loading,
            onScrollPositionChanged = viewModel::onChangeGameListPosition
        )
    }
}
