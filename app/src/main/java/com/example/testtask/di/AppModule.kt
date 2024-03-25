package com.example.testtask.di

import com.example.testtask.data.remote.MealApi
import com.example.testtask.data.repository.MealRepositoryImpl
import com.example.testtask.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideMealRepositoryImpl(mealApi: MealApi): MealRepository {
        return MealRepositoryImpl(mealApi)
    }
}