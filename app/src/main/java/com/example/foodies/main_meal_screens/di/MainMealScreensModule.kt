package com.example.foodies.main_meal_screens.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.foodies.main_meal_screens.data.dbs.category_db.CategoryDao
import com.example.foodies.main_meal_screens.data.dbs.category_db.CategoryDb
import com.example.foodies.main_meal_screens.data.dbs.meal_db.MealDB
import com.example.foodies.main_meal_screens.data.dbs.meal_db.MealDao
import com.example.foodies.main_meal_screens.data.remote.MealApi
import com.example.foodies.main_meal_screens.data.repository.MealRepositoryImpl
import com.example.foodies.main_meal_screens.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainMealScreensModule {

    //Provide api
    @Provides
    @Singleton
    fun provideMealApi(): MealApi {
        return Retrofit.Builder()
            .baseUrl("https://themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    //Provide dao for local db with meals
    @Provides
    @Singleton
    fun provideMealDao(@ApplicationContext appContext: Context): MealDao {
        return Room.databaseBuilder(
            appContext,
            MealDB::class.java,
            "MealDB"
        ).build().MealDao()
    }

    //Provide dao for local db with categories
    @Provides
    @Singleton
    fun provideCategoryDao(@ApplicationContext appContext: Context): CategoryDao {
        return Room.databaseBuilder(
            appContext,
            CategoryDb::class.java,
            "categoryDb"
        ).build().CategoryDao()
    }

    //Provide internet connection(boolean)
    @Provides
    @Singleton
    fun provideInternetConnectionCheck(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

    //Provide repository implementation
    @Provides
    @Singleton
    fun provideMealRepositoryImpl(
        mealApi: MealApi,
        mealDao: MealDao,
        categoryDao: CategoryDao,
        internetConnection: Boolean
    ): MealRepository {
        return MealRepositoryImpl(mealApi, mealDao, categoryDao, internetConnection)
    }
}