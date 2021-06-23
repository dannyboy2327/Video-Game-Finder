package com.anomalydev.videogamefinder.framework.presentation.ui.screens.game_list



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.framework.presentation.theme.VideoGameFinderTheme
import com.anomalydev.videogamefinder.framework.presentation.ui.components.FavoriteGameList
import com.anomalydev.videogamefinder.framework.presentation.ui.components.GameList
import com.anomalydev.videogamefinder.framework.presentation.ui.components.SearchBar


@ExperimentalComposeUiApi
@Composable
fun GameListScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: GameListViewModel,
    onNavigateToGameDetailScreen: (String) -> Unit,
    onNavigateToSettingsScreen: (String) -> Unit,
    ) {

    viewModel.onTriggerEvent(GameListEvents.GetFavoriteGames)

    val games = viewModel.games.value

    val favoriteGames = viewModel.favoriteGames.value

    val loading = viewModel.loading.value

    val loadingFavoriteGames = viewModel.loadingFavoriteGames.value

    val query = viewModel.query.value

    val page = viewModel.page.value

    val dialogQueue  = viewModel.dialogQueue

    VideoGameFinderTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        dialogQueue = dialogQueue.queue.value
    ) {
        Scaffold(
            topBar = {
                SearchBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(GameListEvents.SearchGamesEvent)
                    },
                    onNavigateToSettingsScreen = onNavigateToSettingsScreen
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {

                Text(
                    text = "Games",
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 8.dp,
                        ),
                    style = MaterialTheme.typography.h5,
                )

                GameList(
                    games = games,
                    loading = loading,
                    page = page,
                    onScrollPositionChanged = viewModel::onChangeGameListPosition,
                    onTriggerNextPage = {
                        viewModel.onTriggerEvent(GameListEvents.SearchNextPageEvent)
                    },
                    onNavigateToGameDetailScreen = onNavigateToGameDetailScreen
                )

                if (favoriteGames.isNotEmpty()) {
                    Text(
                        text = "Favorite Games",
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 8.dp,
                            ),
                        style = MaterialTheme.typography.h5,
                    )

                    FavoriteGameList(
                        favoriteGames = favoriteGames,
                        onNavigateToGameDetailScreen = onNavigateToGameDetailScreen,
                    )
                }
            }
        }
    }
}
