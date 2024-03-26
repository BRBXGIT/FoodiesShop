package com.example.testtask.domain.repository

import com.example.testtask.data.db.OfflineCategory
import com.example.testtask.data.db.OfflineMeal
import com.example.testtask.data.remote.CategoryList
import com.example.testtask.data.remote.MealList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MealRepository {

    //Api functions
    suspend fun getCategories(): Response<CategoryList>

    suspend fun getMeals(): Response<MealList>

    //Local db functions
    suspend fun upsertMeal(meal: OfflineMeal)

    fun getOfflineMealsByCategory(category: String): Flow<List<OfflineMeal>>

    suspend fun upsertCategories(category: OfflineCategory)

    fun getAllOfflineCategories(): Flow<List<OfflineCategory>>
}