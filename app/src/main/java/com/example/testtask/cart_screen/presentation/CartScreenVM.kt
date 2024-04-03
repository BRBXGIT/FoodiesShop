package com.example.testtask.cart_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.cart_screen.data.db.Product
import com.example.testtask.cart_screen.data.remote.product.CartMeal
import com.example.testtask.cart_screen.data.repository.CartRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenVM @Inject constructor(
    private val cartRepositoryImpl: CartRepositoryImpl
): ViewModel() {

    //Local db functions
    fun getAllProductsFromDb(): Flow<List<Product>> {
        return cartRepositoryImpl.getAllProducts()
    }

    fun updateExistingProductFromDB(product: Product) {
        viewModelScope.launch {
            cartRepositoryImpl.updateExistingProduct(product)
        }
    }

    fun deleteProductFromDb(product: Product) {
        viewModelScope.launch {
            cartRepositoryImpl.deleteExistingProduct(product)
        }
    }

    //Api functions
    var cartMeals = listOf(CartMeal())
    fun updateCartMealList() {
        viewModelScope.launch {
            val products = getAllProductsFromDb().first()
            for(product in products) {
                if(!checkIsMealInList(product.name)) {
                    cartMeals += cartRepositoryImpl.getProductByName(product.name).body()!!.meals
                }
            }
        }
    }

    private fun checkIsMealInList(name: String): Boolean {
        var isInList = false
        for(cartMeal in cartMeals) {
            if(name == cartMeal.strMeal) {
                isInList = true
            }
        }

        return isInList
    }
}