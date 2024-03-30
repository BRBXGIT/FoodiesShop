package com.example.testtask.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.data.db.category_db.OfflineCategory
import com.example.testtask.data.db.meal_db.OfflineMeal
import com.example.testtask.data.remote.category.Category
import com.example.testtask.data.remote.category.CategoryList
import com.example.testtask.data.remote.meal.Meal
import com.example.testtask.data.remote.meal.MealList
import com.example.testtask.data.repository.MealRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mealRepositoryImpl: MealRepositoryImpl,
): ViewModel() {

    //Get internet connection
    val internetConnection = mealRepositoryImpl.getInternetConnection()

    //All categories offline and online
    var categories by mutableStateOf(CategoryList(listOf(Category())))
    var offlineCategories = mealRepositoryImpl.getAllOfflineCategories()

    //Category chosen by user
    var chosenCategory by mutableStateOf("Beef")

    fun getCategories() {
        viewModelScope.launch {
            try {
                if(internetConnection) {
                    categories = mealRepositoryImpl.getCategories().body()!!
                    val offlineCategories = mealRepositoryImpl.getAllOfflineCategories().first().size

                    //Load categories to local db if it's empty
                    if(offlineCategories == 0) {
                        categories.categories.forEach { category ->
                            mealRepositoryImpl.upsertCategories(OfflineCategory(category.strCategory))
                        }
                    }
                }

            } catch(e: Exception) {
                Log.d("package:mine", e.toString())
            }
        }
    }

    var meals by mutableStateOf(MealList(listOf(Meal())))
    fun getMeals() {
        viewModelScope.launch {
            try {
                if(internetConnection) {
                    meals = mealRepositoryImpl.getMeals().body()!!

                    //Load data to db if it's empty
                    val sortedMeals = emptyList<Meal>().toMutableList()
                    for(meal in meals.meals) {
                        if(meal.strCategory == chosenCategory) {
                            sortedMeals += meal
                        }
                    }

                    //Hardcode again, but i think it's enough for preview)
                    val ingredients = "${sortedMeals[0].strIngredient1}, " +
                            "${sortedMeals[0].strIngredient2}, " +
                            "${sortedMeals[0].strIngredient3}, " +
                            "${sortedMeals[0].strIngredient4}... "

                    if(mealRepositoryImpl.getOfflineMealsByCategory(chosenCategory).first().isEmpty()) {
                        mealRepositoryImpl.upsertMeal(
                            OfflineMeal(
                            title = sortedMeals[0].strMeal,
                            ingredients = ingredients,
                            cost = "от 365 р",
                            category = sortedMeals[0].strCategory
                        )
                        )
                    }
                }
            } catch(e: Exception) {
                Log.d("package:mine", e.toString())
            }
        }
    }

    //Get offline meals from local db if internet connection is false
    fun getOfflineMeals(): Flow<List<OfflineMeal>> {
        return mealRepositoryImpl.getOfflineMealsByCategory(chosenCategory)
    }
}


