package com.example.foodies.main_meal_screens.domain.repository

import com.example.foodies.main_meal_screens.data.dbs.category_db.OfflineCategory
import com.example.foodies.main_meal_screens.data.dbs.meal_db.OfflineMeal
import com.example.foodies.main_meal_screens.data.remote.category.CategoryList
import com.example.foodies.main_meal_screens.data.remote.meal.MealList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MealRepository {

    //Api functions
    suspend fun getCategories(): Response<CategoryList>

    suspend fun getMeals(): Response<MealList>

    suspend fun getMealByName(name: String): Response<MealList>

    fun getInternetConnection(): Boolean

    //Local db functions
    suspend fun upsertMeal(meal: OfflineMeal)

    fun getOfflineMealsByCategory(category: String): Flow<List<OfflineMeal>>

    suspend fun upsertCategories(category: OfflineCategory)

    fun getAllOfflineCategories(): Flow<List<OfflineCategory>>
}