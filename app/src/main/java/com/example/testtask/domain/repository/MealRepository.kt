package com.example.testtask.domain.repository

import com.example.testtask.data.db.category_db.OfflineCategory
import com.example.testtask.data.db.meal_db.OfflineMeal
import com.example.testtask.data.remote.category.CategoryList
import com.example.testtask.data.remote.meal.MealList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MealRepository {

    //Api functions
    suspend fun getCategories(): Response<CategoryList>

    suspend fun getMeals(): Response<MealList>

    fun getInternetConnection(): Boolean

    //Local db functions
    suspend fun upsertMeal(meal: OfflineMeal)

    fun getOfflineMealsByCategory(category: String): Flow<List<OfflineMeal>>

    suspend fun upsertCategories(category: OfflineCategory)

    fun getAllOfflineCategories(): Flow<List<OfflineCategory>>
}