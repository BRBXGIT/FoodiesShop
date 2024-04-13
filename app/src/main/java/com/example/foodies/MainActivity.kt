package com.example.foodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.foodies.auth.google_auth.GoogleAuthUiClient
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.bottom_bar.NavGraph
import com.example.foodies.ui.theme.FoodiesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var googleAuthUiClient: GoogleAuthUiClient

    @Inject lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = preferencesManager.darkModeFlow.collectAsState(initial = null).value

            FoodiesTheme(
                darkTheme = if((darkTheme == null) || (darkTheme == "system")) {
                    isSystemInDarkTheme()
                } else if(darkTheme == "light") {
                    false
                } else {
                    true
                }
            ) {
                NavGraph(
                    googleAuthUiClient = googleAuthUiClient,
                    preferencesManager = preferencesManager
                )
            }
        }
    }
}