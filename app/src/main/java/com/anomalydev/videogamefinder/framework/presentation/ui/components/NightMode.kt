package com.anomalydev.videogamefinder.framework.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anomalydev.videogamefinder.R
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun NightMode() {

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
                }
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
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
        )

        Switch(
            checked = false,
            onCheckedChange = { false },
            modifier = Modifier
                .constrainAs(switch) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Preview
@Composable
fun PreviewNightMode() {
    NightMode()
}