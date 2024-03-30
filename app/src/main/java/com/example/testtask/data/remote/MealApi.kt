package com.example.testtask.data.remote

import com.example.testtask.data.remote.category.CategoryList
import com.example.testtask.data.remote.meal.MealList
import retrofit2.Response
import retrofit2.http.GET

//Api functions
interface MealApi {

    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories(): Response<CategoryList>

    @GET("/api/json/v1/1/search.php?s")
    suspend fun getMeals(): Response<MealList>
}