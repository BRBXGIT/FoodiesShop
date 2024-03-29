package com.example.testtask.presentation.BottomBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.testtask.presentation.main_screen.MainScreenViewModel
import androidx.navigation.compose.composable
import com.example.testtask.presentation.main_screen.MainScreen

@Composable
fun NavGraph(
    mainScreenViewModel: MainScreenViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable(route = "main_screen") {
            MainScreen(mainScreenViewModel = mainScreenViewModel)
        }

        composable(route = "profile_screen") {

        }

        composable(route = "cart_screen") {

        }
    }
}