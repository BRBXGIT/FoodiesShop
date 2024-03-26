package com.example.testtask.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.data.db.OfflineCategory
import com.example.testtask.data.db.OfflineMeal
import com.example.testtask.data.remote.Category
import com.example.testtask.data.remote.CategoryList
import com.example.testtask.data.remote.Meal
import com.example.testtask.data.remote.MealList
import com.example.testtask.data.repository.MealRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mealRepositoryImpl: MealRepositoryImpl
): ViewModel() {

    var noInternet = false

    //Function for getting categories from db
    var categories by mutableStateOf(CategoryList(listOf(Category())))
    var offlineCategories = mealRepositoryImpl.getAllOfflineCategories()
    var chosenCategory by mutableStateOf("Beef")
    fun getCategories() {
        viewModelScope.launch {
            try {
                categories = mealRepositoryImpl.getCategories().body()!!
                val offlineCategories = mealRepositoryImpl.getAllOfflineCategories().first().size

                if(offlineCategories == 0) {
                    categories.categories.forEach { category ->  
                        mealRepositoryImpl.upsertCategories(OfflineCategory(category.strCategory))
                    }
                }

            } catch(e: Exception) {
                noInternet = true
            }
        }
    }

    //Function for getting meals from db
    var meals by mutableStateOf(MealList(listOf(Meal())))
    val offlineMeals = mealRepositoryImpl.getOfflineMealsByCategory(chosenCategory)
    fun getMeals() {
        viewModelScope.launch {
            try {
                meals = mealRepositoryImpl.getMeals().body()!!
                val offlineMeals = mealRepositoryImpl.getOfflineMealsByCategory(chosenCategory).first().size

                if(offlineMeals == 0) {

                    val sortedMeals = emptyList<Meal>().toMutableList()
                    for(item in meals.meals) {
                        if(item.strCategory == chosenCategory) {
                            sortedMeals += item
                            break
                        }
                    }

                    val ingredients = "${sortedMeals[0].strIngredient1}, " +
                            "${sortedMeals[0].strIngredient2}, " +
                            "${sortedMeals[0].strIngredient3}, " +
                            "${sortedMeals[0].strIngredient4}, " +
                            "${sortedMeals[0].strIngredient5}, " +
                            "${sortedMeals[0].strIngredient6}, "

                    mealRepositoryImpl.upsertMeal(
                        OfflineMeal(
                            id = 0,
                            title = sortedMeals[0].strMeal,
                            ingredients = ingredients,
                            cost = "от 365 р",
                            category = sortedMeals[0].strCategory
                        )
                    )
                }
            } catch(e: Exception) {
                noInternet = true
            }
        }
    }
}


