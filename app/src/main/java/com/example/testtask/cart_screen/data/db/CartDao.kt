package com.example.testtask.cart_screen.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//Dao for cart
@Dao
interface CartDao {

    @Upsert
    suspend fun upsertNewCartMeal(cartMeal: CartMeal)

    @Update
    suspend fun updateCartMeal(cartMeal: CartMeal)

    @Delete
    suspend fun deleteCartMeal(cartMeal: CartMeal)

    @Query("SELECT * FROM cartmeal")
    fun getAllCartMeals(): Flow<List<CartMeal>>

    @Query("SELECT EXISTS(SELECT * FROM cartmeal WHERE name = :name)")
    suspend fun checkIsMealInCart(name: String): Boolean
}