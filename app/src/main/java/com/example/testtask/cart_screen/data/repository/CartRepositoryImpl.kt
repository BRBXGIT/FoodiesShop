package com.example.testtask.cart_screen.data.repository

import com.example.testtask.cart_screen.data.db.CartDao
import com.example.testtask.cart_screen.data.db.CartMeal
import com.example.testtask.cart_screen.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
): CartRepository {

    //Local db functions
    override suspend fun upsertNewCartMeal(cartMeal: CartMeal) {
        cartDao.upsertNewCartMeal(cartMeal)
    }

    override suspend fun updateCartMeal(cartMeal: CartMeal) {
        cartDao.updateCartMeal(cartMeal)
    }

    override suspend fun deleteCartMeal(cartMeal: CartMeal) {
        cartDao.deleteCartMeal(cartMeal)
    }

    override fun getAllCartMeals(): Flow<List<CartMeal>> {
        return cartDao.getAllCartMeals()
    }

    override suspend fun checkIsMealInCart(name: String): Boolean {
        return cartDao.checkIsMealInCart(name)
    }
}