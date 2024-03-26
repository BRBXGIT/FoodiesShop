package com.example.testtask.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.data.remote.Category
import com.example.testtask.data.remote.CategoryList
import com.example.testtask.data.remote.Meal
import com.example.testtask.data.remote.MealList
import com.example.testtask.data.repository.MealRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mealRepositoryImpl: MealRepositoryImpl
): ViewModel() {

    //Function for getting categories from db
    var categories by mutableStateOf(CategoryList(listOf(Category())))
    var chosenCategory by mutableStateOf("Beef")
    fun getCategories() {
        viewModelScope.launch {
            try {
                categories = mealRepositoryImpl.getCategories().body()!!
            } catch(e: Exception) {
                Log.d("package:mine", e.toString())
            }
        }
    }

    //Function for getting meals from db
    var meals by mutableStateOf(MealList(listOf(Meal())))
    fun getMeals() {
        viewModelScope.launch {
            try {
                meals = mealRepositoryImpl.getMeals().body()!!
            } catch(e: Exception) {
                Log.d("package:mine", e.toString())
            }
        }
    }

}


