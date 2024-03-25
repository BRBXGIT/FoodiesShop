package com.example.testtask.domain.repository

import com.example.testtask.data.remote.CategoryList
import com.example.testtask.data.remote.MealList
import retrofit2.Response

interface MealRepository {

    suspend fun getCategories(): Response<CategoryList>

    suspend fun getMeals(): Response<MealList>
}