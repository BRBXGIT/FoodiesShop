package com.example.testtask.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.testtask.data.db.CategoryDao
import com.example.testtask.data.db.CategoryDb
import com.example.testtask.data.db.MealDB
import com.example.testtask.data.db.MealDao
import com.example.testtask.data.remote.MealApi
import com.example.testtask.data.repository.MealRepositoryImpl
import com.example.testtask.domain.repository.MealRepository
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
object AppModule {

    @Provides
    @Singleton
    fun provideMealApi(): MealApi {
        return Retrofit.Builder()
            .baseUrl("https://themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMealDao(@ApplicationContext appContext: Context): MealDao {
        return Room.databaseBuilder(
            appContext,
            MealDB::class.java,
            "MealDb"
        ).build().MealDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(@ApplicationContext appContext: Context): CategoryDao {
        return Room.databaseBuilder(
            appContext,
            CategoryDb::class.java,
            "categoryDb"
        ).build().CategoryDao()
    }

    @Provides
    @Singleton
    fun provideInternetConnectionCheck(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

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