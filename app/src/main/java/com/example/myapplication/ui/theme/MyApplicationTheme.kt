package com.example.myapplication.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomColorScheme = lightColorScheme(
    primary = Color(0xFFFF9800), // Primary (Orange)
    onPrimary = Color.White,    // Text on Primary
    secondary = Color.Black,    // Secondary
    onSecondary = Color.White,  // Text on Secondary
    background = Color(0xFFFFFFFF), // Background
    onBackground = Color(0xFF333333), // Text on Background
    surface = Color(0xFFFFFFFF),      // Surface (White)
    onSurface = Color(0xFF333333)    // Text on Surface
)

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = Typography, // Use Material3 default typography or define custom
        content = content
    )
}
