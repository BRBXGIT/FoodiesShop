package com.example.foodies.settings_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

@Composable
fun RowScope.ThemeElement(
    title: String,
    preferencesManager: PreferencesManager,
    scope: CoroutineScope
) {

    val selectedTheme = preferencesManager.darkModeFlow.collectAsState(initial = null).value
    var chosen = false
    if(((selectedTheme == null) || (selectedTheme == "system")) && (title == "Система")) {
        chosen = true
    }
    if((selectedTheme == "light") && (title == "Светлая")) {
        chosen = true
    }
    if((selectedTheme == "dark") && (title == "Тёмная")) {
        chosen = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .background(
                if (chosen) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                }
            )
            .clickable {
                if(title == "Система") {
                    scope.launch {
                        preferencesManager.storeDarkMode("system")
                    }
                }
                if(title == "Тёмная") {
                    scope.launch {
                        preferencesManager.storeDarkMode("dark")
                    }
                }
                if(title == "Светлая") {
                    scope.launch {
                        preferencesManager.storeDarkMode("light")
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(chosen) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Chosen icon",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = title,
                color = if(chosen) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onTertiary
                },
                fontSize = 14.sp
            )
        }
    }
}