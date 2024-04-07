package com.example.testtask.bottom_bar

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testtask.cart_screen.presentation.CartScreen
import com.example.testtask.cart_screen.presentation.CartScreenVM
import com.example.testtask.main_meal_screens.presentation.main_screen.MainScreen
import com.example.testtask.main_meal_screens.presentation.meal_screen.MealScreen
import com.example.testtask.profile_screen.presentation.ProfileScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavGraph() {

    //Initialize systemUiController
    val systemUiController = rememberSystemUiController()

    //Initialize viewModels and navController
    val mainMealScreensVM = hiltViewModel<MainMealScreensVM>()
    val cartScreenVM = hiltViewModel<CartScreenVM>()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable(
            route = "main_screen",
            enterTransition = {
                fadeIn(tween(400))
            },
            exitTransition = {
                fadeOut(tween(400))
            }
        ) {
            MainScreen(
                mainMealScreensVM = mainMealScreensVM,
                navController = navController,
                systemUiController = systemUiController,
            )
        }

        composable(
            route = "profile_screen",
            enterTransition = {
                fadeIn(tween(400))
            },
            exitTransition = {
                fadeOut(tween(400))
            }
        ) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = "cart_screen",
            enterTransition = {
                fadeIn(tween(400))
            },
            exitTransition = {
                fadeOut(tween(400))
            }
        ) {
            CartScreen(
                navController = navController,
                systemUiController = systemUiController,
                cartScreenVM = cartScreenVM,
                mainMealScreensVM = mainMealScreensVM
            )
        }

        composable(
            route = "meal_screen",
            enterTransition = { slideInHorizontally(
                initialOffsetX = { 400 },
                animationSpec = tween(
                    durationMillis = 400,
                )
            ) + fadeIn(tween(400)) },
            popExitTransition = { slideOutHorizontally(
                targetOffsetX = { 400 },
                animationSpec = tween(
                    durationMillis = 400
                )
            ) + fadeOut(tween(400)) },
        ) {
            MealScreen(
                navController = navController,
                mainMealScreensVM = mainMealScreensVM,
                systemUiController = systemUiController,
                cartScreenVM = cartScreenVM
            )
        }
    }
}