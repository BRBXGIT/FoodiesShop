package com.example.testtask.presentation.main_screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testtask.data.remote.Meal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state = rememberLazyListState()
    var visible by rememberSaveable { mutableStateOf(!state.canScrollBackward) }

    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->
        Column {
            TopBar(
                visible = visible,
                mainScreenViewModel = mainScreenViewModel
            )

            mainScreenViewModel.getMeals()

            //Meals sorted by category
            val meals = emptyList<Meal>().toMutableList()
            for(meal in mainScreenViewModel.meals.meals) {
                if(meal.strCategory == mainScreenViewModel.chosenCategory) {
                    meals += meal
                }
            }

            visible = if(meals.size == 2) {
                false
            } else {
                !state.canScrollBackward
            }

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
                items(meals) { meal ->

                    val ingredients = "${meal.strIngredient1}, " +
                            "${meal.strIngredient2}, " +
                            "${meal.strIngredient3}, " +
                            "${meal.strIngredient4}, " +
                            "${meal.strIngredient5}, " +
                            "${meal.strIngredient6}, "

                    MealElement(
                        image = meal.strMealThumb,
                        title = meal.strMeal,
                        ingredients = ingredients,
                    )
                }
            }
        }
    }
}