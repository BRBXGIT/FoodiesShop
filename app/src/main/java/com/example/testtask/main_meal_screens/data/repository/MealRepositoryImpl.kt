package com.example.testtask.main_meal_screens.data.repository

import com.example.testtask.main_meal_screens.data.db.category_db.CategoryDao
import com.example.testtask.main_meal_screens.data.db.meal_db.MealDao
import com.example.testtask.main_meal_screens.data.db.category_db.OfflineCategory
import com.example.testtask.main_meal_screens.data.db.meal_db.OfflineMeal
import com.example.testtask.main_meal_screens.data.remote.category.CategoryList
import com.example.testtask.main_meal_screens.data.remote.MealApi
import com.example.testtask.main_meal_screens.data.remote.meal.MealList
import com.example.testtask.main_meal_screens.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao,
    private val categoryDao: CategoryDao,
    private val internetConnection: Boolean
): MealRepository {

    //Api functions
    override suspend fun getCategories(): Response<CategoryList> {
        return mealApi.getCategories()
    }

    override suspend fun getMeals(): Response<MealList> {
        return mealApi.getMeals()
    }

    override suspend fun getMealByName(name: String): Response<MealList> {
        return mealApi.getMealByName(name)
    }

    override fun getInternetConnection(): Boolean {
        return internetConnection
    }

    //Local db functions
    override fun getOfflineMealsByCategory(category: String): Flow<List<OfflineMeal>> {
        return mealDao.getMealsByCategory(category)
    }

    override suspend fun upsertMeal(meal: OfflineMeal) {
        mealDao.upsertNewMeal(meal)
    }

    override suspend fun upsertCategories(category: OfflineCategory) {
        categoryDao.upsertAllCategories(category)
    }

    override fun getAllOfflineCategories(): Flow<List<OfflineCategory>> {
        return categoryDao.getAllCategories()
    }
}