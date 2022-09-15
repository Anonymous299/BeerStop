package com.trype.core.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.trype.core.R

val Playfair = FontFamily(
    Font(R.font.playfairdisplay_regular)
)

val Raleway = FontFamily(
    Font(R.font.raleway_variable_font_wght)
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 9.5.sp
    ),
    h1 = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight(700),
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp
    ),
    h4 = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight(700),
        fontSize = 12.sp
    ),
    body2 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W900,
        fontSize = 10.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)