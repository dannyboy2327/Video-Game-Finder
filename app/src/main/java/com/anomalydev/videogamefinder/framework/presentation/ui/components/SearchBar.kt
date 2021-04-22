package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.framework.presentation.ui.navigation.Screen

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onNavigateToSettingsScreen: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val keyboardController = LocalSoftwareKeyboardController.current

            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(6.dp)
                    .background(color = MaterialTheme.colors.primaryVariant, shape = CircleShape),
                value = query,
                onValueChange = { newQuery ->
                    onQueryChanged(newQuery)
                },
                label = {
                    Text(
                        text = "Search",
                        color = Color.White
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onExecuteSearch()
                        keyboardController?.hide()
                    }
                ),
                shape = CircleShape
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    val route = Screen.Settings.route
                    onNavigateToSettingsScreen(route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                )
            }
        }
    }
}
