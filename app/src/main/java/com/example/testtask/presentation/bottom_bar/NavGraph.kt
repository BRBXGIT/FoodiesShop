package com.example.testtask.presentation.bottom_bar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.example.testtask.presentation.main_screen.MainScreenViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testtask.presentation.cart_screen.CartScreen
import com.example.testtask.presentation.main_screen.main_screen.MainScreen
import com.example.testtask.presentation.main_screen.meal_screen.MealScreen
import com.example.testtask.presentation.profile_screen.ProfileScreen

@Composable
fun NavGraph() {

    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable(route = "main_screen") {
            MainScreen(
                mainScreenViewModel = mainScreenViewModel,
                navController = navController
            )
        }

        composable(route = "profile_screen") {
            ProfileScreen(navController = navController)
        }

        composable(route = "cart_screen") {
            CartScreen(navController = navController)
        }

        composable(route = "meal_screen") {
            MealScreen(
                navController = navController,
                mainScreenViewModel = mainScreenViewModel
            )
        }
    }
}