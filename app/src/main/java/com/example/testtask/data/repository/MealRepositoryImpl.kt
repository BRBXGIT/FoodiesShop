package com.example.testtask.data.repository

import com.example.testtask.data.remote.CategoryList
import com.example.testtask.data.remote.MealApi
import com.example.testtask.data.remote.MealList
import com.example.testtask.domain.repository.MealRepository
import retrofit2.Response
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi
): MealRepository {

    override suspend fun getCategories(): Response<CategoryList> {
        return mealApi.getCategories()
    }

    override suspend fun getMeals(): Response<MealList> {
        return mealApi.getMeals()
    }
}