package com.alageek.ueca.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColors = darkColors(
    primary = COLOR_PURPLE_200,
    primaryVariant = COLOR_PURPLE_700,
    secondary = COLOR_TEAL_200
)

private val LightColors = lightColors(
    primary = COLOR_PURPLE_500,
    primaryVariant = COLOR_PURPLE_700,
    secondary = COLOR_TEAL_200
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = when (darkTheme) {
        true -> DarkColors
        else -> LightColors
    }
    MaterialTheme(colors = colors, content = content)
}
