package com.example.testtask.main_meal_screens.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.testtask.bottom_bar.presentation.BottomBar
import com.example.testtask.cart_screen.presentation.CartScreenVM
import com.example.testtask.main_meal_screens.data.remote.meal.Meal
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun MainScreen(
    mainMealScreensVM: MainMealScreensVM,
    navController: NavHostController,
    systemUiController: SystemUiController,
) {

    //Change colors of system bars
    SideEffect {
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
    }

    //Check visible of advertising banners
    val state = rememberLazyListState()
    var visible by rememberSaveable { mutableStateOf(!state.canScrollBackward) }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->

        //Main column
        Column {
            TopBar(
                visible = visible,
                mainMealScreensVM = mainMealScreensVM
            )

            //Get meals from api
            //Or if it's no internet connection load it from local db
            mainMealScreensVM.getMeals()

            //Meals sorted by category
            val meals = emptyList<Meal>().toMutableList()
            for(meal in mainMealScreensVM.meals.meals) {
                if(meal.strCategory == mainMealScreensVM.chosenCategory) {
                    meals += meal
                }
            }

            //Update visible var
            visible = !state.canScrollBackward

            //Offline meals sorted by category
            val offlineMeals = mainMealScreensVM.getOfflineMeals().collectAsState(
                initial = emptyList()
            ).value

            //LazyColumn with meals
            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xfffbfbfb))
                    .padding(
                        PaddingValues(
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Checking internet connection and load meals from db if it's false
                if(mainMealScreensVM.internetConnection) {
                    items(meals) { meal ->

                        //Hardcode again, i described this method in ViewModel
                        val ingredients = "${meal.strIngredient1}, " +
                                "${meal.strIngredient2}, " +
                                "${meal.strIngredient3}, " +
                                "${meal.strIngredient4}... "

                        MealElement(
                            image = meal.strMealThumb,
                            title = meal.strMeal,
                            ingredients = ingredients,
                            navController = navController,
                            mainMealScreensVM = mainMealScreensVM
                        )
                    }
                } else {
                    items(offlineMeals) { meal ->
                        MealElement(
                            image = null,
                            title = meal.title,
                            ingredients = meal.ingredients,
                            navController = navController,
                            mainMealScreensVM = mainMealScreensVM
                        )
                    }
                }
            }
        }
    }
}