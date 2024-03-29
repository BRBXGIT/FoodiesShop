package com.example.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.testtask.presentation.BottomBar.NavGraph
import com.example.testtask.presentation.main_screen.MainScreen
import com.example.testtask.presentation.main_screen.MainScreenViewModel
import com.example.testtask.ui.theme.TestTaskTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskTheme {
                //Change system bars colors
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
                    systemUiController.setStatusBarColor(Color(0xfffbfbfb))
                }

                //I decided to fully create this app, and add navigation and other things
                //I will add few functions for api, such as get meal by id
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                val navController = rememberNavController()
                NavGraph(
                    mainScreenViewModel = mainScreenViewModel,
                    navController = navController
                )
            }
        }
    }
}