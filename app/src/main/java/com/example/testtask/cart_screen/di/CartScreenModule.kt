package com.example.testtask.cart_screen.di

import android.content.Context
import androidx.room.Room
import com.example.testtask.cart_screen.data.db.CartDao
import com.example.testtask.cart_screen.data.db.CartDb
import com.example.testtask.cart_screen.data.remote.CartApi
import com.example.testtask.cart_screen.data.repository.CartRepositoryImpl
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
object CartScreenModule {

    @Provides
    @Singleton
    fun provideCartDao(@ApplicationContext appContext: Context): CartDao {
        return Room.databaseBuilder(
            appContext,
            CartDb::class.java,
            "CartDb"
        ).build().CartDao()
    }

    @Provides
    @Singleton
    fun provideCartApi(): CartApi {
        return Retrofit.Builder()
            .baseUrl("https://themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCartRepositoryImpl(cartDao: CartDao, cartApi: CartApi): CartRepositoryImpl {
        return CartRepositoryImpl(cartApi = cartApi, cartDao = cartDao)
    }
}