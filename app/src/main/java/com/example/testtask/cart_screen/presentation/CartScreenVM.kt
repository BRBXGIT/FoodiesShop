package com.example.testtask.cart_screen.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.cart_screen.data.db.Product
import com.example.testtask.cart_screen.data.remote.product.CartMeal
import com.example.testtask.cart_screen.data.remote.product.CartMealList
import com.example.testtask.cart_screen.data.repository.CartRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    fun upsertNewProductToDb(product: Product) {
        viewModelScope.launch {
            cartRepositoryImpl.upsertNewProduct(product)
        }
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
    var cartMeal by mutableStateOf(CartMealList(listOf(CartMeal())))
    fun getProductByName(name: String) {
        viewModelScope.launch {
            cartMeal = cartRepositoryImpl.getProductByName(name).body()!!
        }
    }
}