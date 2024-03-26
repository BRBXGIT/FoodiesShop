package com.example.testtask.presentation.main_screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testtask.data.remote.Meal

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    //Check visible of advertising banners
    val state = rememberLazyListState()
    var visible by rememberSaveable { mutableStateOf(!state.canScrollBackward) }

    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->

        //Main column
        Column {
            TopBar(
                visible = visible,
                mainScreenViewModel = mainScreenViewModel
            )

            //Get meals from api
            //Or if it's no internet connection load it from local db
            mainScreenViewModel.getMeals()

            //Meals sorted by category
            val meals = emptyList<Meal>().toMutableList()
            for(meal in mainScreenViewModel.meals.meals) {
                if(meal.strCategory == mainScreenViewModel.chosenCategory) {
                    meals += meal
                }
            }

            //Update visible var
            visible = !state.canScrollBackward

            //Offline meals sorted by category
            val offlineMeals = mainScreenViewModel.getOfflineMeals().collectAsState(
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
                if(mainScreenViewModel.internetConnection) {
                    items(meals) { meal ->

                        //Hardcode but it works
                        //I think it's enough for preview
                        val ingredients = "${meal.strIngredient1}, " +
                                "${meal.strIngredient2}, " +
                                "${meal.strIngredient3}, " +
                                "${meal.strIngredient4}... "

                        MealElement(
                            image = meal.strMealThumb,
                            title = meal.strMeal,
                            ingredients = ingredients,
                        )
                    }
                } else {
                    items(offlineMeals) { meal ->
                        MealElement(
                            image = null,
                            title = meal.title,
                            ingredients = meal.ingredients,
                        )
                    }
                }
            }
        }
    }
}