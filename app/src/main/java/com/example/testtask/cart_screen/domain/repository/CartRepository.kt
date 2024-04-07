package com.example.testtask.cart_screen.domain.repository

import com.example.testtask.cart_screen.data.db.CartMeal
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun upsertNewCartMeal(cartMeal: CartMeal)

    suspend fun updateCartMeal(cartMeal: CartMeal)

    suspend fun deleteCartMeal(cartMeal: CartMeal)

    fun getAllCartMeals(): Flow<List<CartMeal>>

    suspend fun checkIsMealInCart(name: String): Boolean
}