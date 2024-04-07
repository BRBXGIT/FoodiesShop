package com.example.testtask.cart_screen.di

import android.content.Context
import androidx.room.Room
import com.example.testtask.cart_screen.data.db.CartDao
import com.example.testtask.cart_screen.data.db.CartDb
import com.example.testtask.cart_screen.data.repository.CartRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        ).build().cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepositoryImpl(cartDao: CartDao): CartRepositoryImpl {
        return CartRepositoryImpl(cartDao)
    }
}