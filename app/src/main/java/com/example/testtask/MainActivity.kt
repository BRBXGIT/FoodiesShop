package com.example.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testtask.presentation.main_screen.MainScreen
import com.example.testtask.presentation.main_screen.MainScreenViewModel
import com.example.testtask.ui.theme.TestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskTheme {
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                MainScreen(mainScreenViewModel)
            }
        }
    }
}