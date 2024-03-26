package com.example.testtask.presentation.main_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testtask.data.remote.Meal
import kotlin.reflect.full.memberProperties

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state = rememberLazyListState()

    Scaffold {
        Column {
            TopBar(
                visible = !state.canScrollBackward,
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

            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xfffbfbfb)),
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