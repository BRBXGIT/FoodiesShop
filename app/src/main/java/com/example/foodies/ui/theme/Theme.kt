package com.example.foodies.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.foodies.MainActivity

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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun FoodiesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity = LocalContext.current as MainActivity,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val window = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current

    var appDimens = compactMediumDimens

    when(window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            appDimens = if((config.screenHeightDp) <= 640) {
                compactSmallDimens
            } else if((config.screenHeightDp) <= 800) {
                compactSmallMediumDimens
            } else if((config.screenHeightDp) <= 920) {
                compactMediumDimens
            } else {
                compactLargeDimens
            }
        }
    }

    Log.d("XXXX", config.screenHeightDp.toString())

    AppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current