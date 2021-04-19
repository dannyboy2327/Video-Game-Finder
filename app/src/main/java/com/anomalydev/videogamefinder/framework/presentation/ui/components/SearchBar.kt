package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val keyboardController = LocalSoftwareKeyboardController.current

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query,
                onValueChange = { newQuery ->
                    onQueryChanged(newQuery)
                },
                label = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
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
        }
    }
}
