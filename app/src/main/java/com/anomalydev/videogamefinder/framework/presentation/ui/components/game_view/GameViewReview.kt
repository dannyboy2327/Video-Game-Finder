package com.anomalydev.videogamefinder.framework.presentation.ui.components.game_view


import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anomalydev.videogamefinder.business.domain.model.Rating

@Composable
fun GameViewReview(
    rating: Rating,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 6.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .background(
                color = Color.Gray,
                shape = CircleShape,
            ),
    ) {
        Text(
            text = rating.title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth(0.50f)
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 8.dp,
                )
        )

        Text(
            text = rating.count.toString(),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth(0.50f)
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp,
                )
        )
    }

}