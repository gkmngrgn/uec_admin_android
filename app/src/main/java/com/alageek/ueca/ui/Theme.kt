package com.alageek.ueca.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.alageek.ueca.R


@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = when (darkTheme) {
        true -> darkColors(
            primary = colorResource(id = R.color.color_2),
            primaryVariant = colorResource(id = R.color.color_1),
            secondary = colorResource(id = R.color.color_3),
            secondaryVariant = colorResource(id = R.color.color_4),
            background = colorResource(id = R.color.color_1),
            surface = colorResource(id = R.color.color_1),
            error = colorResource(id = R.color.color_5),
            onPrimary = colorResource(id = R.color.color_8),
            onSecondary = colorResource(id = R.color.color_7),
            onBackground = colorResource(id = R.color.color_8),
            onSurface = colorResource(id = R.color.color_8),
            onError = colorResource(id = R.color.color_1),
        )
        else -> lightColors(
            primary = colorResource(id = R.color.color_6),
            primaryVariant = colorResource(id = R.color.color_4),
            secondary = colorResource(id = R.color.color_7),
            secondaryVariant = colorResource(id = R.color.color_5),
            background = colorResource(id = R.color.color_8),
            surface = colorResource(id = R.color.color_8),
            error = colorResource(id = R.color.color_4),
            onPrimary = colorResource(id = R.color.color_3),
            onSecondary = colorResource(id = R.color.color_4),
            onBackground = colorResource(id = R.color.color_1),
            onSurface = colorResource(id = R.color.color_1),
            onError = colorResource(id = R.color.color_8),
        )
    }

    MaterialTheme(colors = colors, content = content)
}
