package com.example.foodies.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    surface = Color(0xff101010), //All surface
    primary = Color(0xffe1e1e1), //All headers
    secondary = Color(0xff979797), //All second parameters, such as description
    tertiary = Color(0xffb07feb), //Buttons
    onTertiaryContainer = Color(0xFF5A4F66), //Secondary buttons
    tertiaryContainer = Color(0xFF161616), //Secondary buttons surface
    onTertiary = Color(0xFF6A6A6D),
    surfaceContainerLowest = Color(0xFF202020),
    background = Color(0xff2e2e2e),
    onBackground = Color(0xff7b7b7b),
    surfaceTint = Color(0xFF17191B),
    outline = Color(0xFF8C70AD)
)

private val LightColorScheme = lightColorScheme(
    surface = Color(0xfffbfbfb), //All surface
    primary = Color(0xff222831), //All headers
    secondary = Color(0xffa9aaad), //All second parameters, such as description
    tertiary = Color(0xfffd3a69), //Buttons
    onTertiaryContainer = Color(0xfff3ccd6), //Secondary buttons
    tertiaryContainer = Color(0xffffffff), //Secondary buttons surface
    onTertiary = Color(0xffc3c4c9),
    surfaceContainerLowest = Color(0xfff5f5f5),
    background = Color(0xfff0f0f0),
    onBackground = Color(0xff7b7b7b),
    surfaceTint = Color(0xfff6f7f9),
    outline = Color(0xFFF897B0)
)

@Composable
fun FoodiesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}