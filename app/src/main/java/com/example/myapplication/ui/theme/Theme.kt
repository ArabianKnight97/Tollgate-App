package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryOrange,
    onPrimary = BackgroundWhite,
    secondary = AccentBlack,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onSurface = AccentBlack
)

private val DarkColors = darkColorScheme(
    primary = PrimaryOrange,
    onPrimary = BackgroundWhite,
    secondary = AccentBlack,
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onSurface = BackgroundWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}
