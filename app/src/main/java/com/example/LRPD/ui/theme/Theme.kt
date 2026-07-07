package com.example.LRPD.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CarrionBlue,
    secondary = CarrionYellow,
    background = CarrionWhite,
    surface = CarrionWhite,
    onPrimary = CarrionWhite,
    onSecondary = CarrionDark // <--- AQUÍ YA NO DARÁ ERROR
)

@Composable
fun Lrp2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}