package com.example.foodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.foodies.auth.google_auth.GoogleAuthUiClient
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.bottom_bar.NavGraph
import com.example.foodies.ui.theme.TestTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var googleAuthUiClient: GoogleAuthUiClient

    @Inject lateinit var preferencesManager: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskTheme {
                NavGraph(
                    googleAuthUiClient = googleAuthUiClient,
                    preferencesManager = preferencesManager
                )
            }
        }
    }
}