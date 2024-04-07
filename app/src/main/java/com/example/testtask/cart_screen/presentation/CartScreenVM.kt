package com.example.testtask.cart_screen.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.cart_screen.data.db.CartMeal
import com.example.testtask.cart_screen.data.repository.CartRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenVM @Inject constructor(
    private val cartRepositoryImpl: CartRepositoryImpl
): ViewModel() {

    fun upsertNewMealToCart(cartMeal: CartMeal) {
        viewModelScope.launch {
            cartRepositoryImpl.upsertNewCartMeal(cartMeal)
        }
    }

    fun updateCartMeal(cartMeal: CartMeal) {
        viewModelScope.launch {
            cartRepositoryImpl.updateCartMeal(cartMeal)
        }
    }

    fun deleteCartMeal(cartMeal: CartMeal) {
        viewModelScope.launch {
            cartRepositoryImpl.deleteCartMeal(cartMeal)
        }
    }

    fun getAllCartMeals(): Flow<List<CartMeal>> {
        return cartRepositoryImpl.getAllCartMeals()
    }

    var isInCart = false
    fun checkIsMealInCart(name: String) {
        viewModelScope.launch {
            isInCart = cartRepositoryImpl.checkIsMealInCart(name)
        }
    }
}