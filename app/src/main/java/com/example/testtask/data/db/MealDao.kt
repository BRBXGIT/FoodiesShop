package com.example.testtask.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//Dao for offline meals
@Dao
interface MealDao {

    @Upsert
    suspend fun upsertNewMeal(meal: OfflineMeal)

    @Query("SELECT * FROM offlinemeal WHERE category = :category")
    fun getMealsByCategory(category: String): Flow<List<OfflineMeal>>
}