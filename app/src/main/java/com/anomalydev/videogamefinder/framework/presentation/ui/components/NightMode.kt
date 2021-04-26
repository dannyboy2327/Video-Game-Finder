package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun NightMode(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {

    val checkedState = remember { mutableStateOf(isDarkTheme)}

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val (icon, text, switch) = createRefs()

        Icon(
            painter = painterResource(id = R.drawable.ic_night_mode),
            contentDescription = "Night Mode",
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            tint = Color.Yellow,
        )

        Text(
            text = "Night Mode",
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(text) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary,
        )

        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onToggleTheme()
                              },
            modifier = Modifier
                .constrainAs(switch) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Red,
                uncheckedThumbColor = Color.White,
            )
        )
    }
}