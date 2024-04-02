package com.example.testtask.bottom_bar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testtask.cart_screen.presentation.CartScreen
import com.example.testtask.main_meal_screens.presentation.main_screen.MainScreen
import com.example.testtask.main_meal_screens.presentation.meal_screen.MealScreen
import com.example.testtask.profile_screen.presentation.ProfileScreen

@Composable
fun NavGraph() {

    val mainMealScreensVM = hiltViewModel<MainMealScreensVM>()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable(route = "main_screen") {
            MainScreen(
                mainMealScreensVM = mainMealScreensVM,
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
                mainMealScreensVM = mainMealScreensVM
            )
        }
    }
}