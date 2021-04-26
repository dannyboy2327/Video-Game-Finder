package com.anomalydev.videogamefinder.framework.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.anomalydev.videogamefinder.R

// Custom fonts for Coda
val Coda = FontFamily(
    Font(R.font.coda_caption_extra_bold, FontWeight.W600),
    Font(R.font.coda_extra_bold, FontWeight.W500),
    Font(R.font.coda_regular, FontWeight.W400),
)

val CodaTypography = Typography(
    h1 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
    ),
    h2 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = Coda,
        fontSize = 14.sp,
    ),
    button = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White,
    ),
    caption = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontFamily = Coda,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
    )
)