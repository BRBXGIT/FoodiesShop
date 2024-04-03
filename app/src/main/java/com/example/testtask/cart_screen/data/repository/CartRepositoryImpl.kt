package com.example.testtask.cart_screen.data.repository

import com.example.testtask.cart_screen.data.db.CartDao
import com.example.testtask.cart_screen.data.db.Product
import com.example.testtask.cart_screen.data.remote.CartApi
import com.example.testtask.cart_screen.data.remote.product.CartMealList
import com.example.testtask.cart_screen.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val cartApi: CartApi
): CartRepository {

    //Api functions
    override suspend fun getProductByName(name: String): Response<CartMealList> {
        return cartApi.getProductByName(name)
    }


    //Local db functions
    override suspend fun upsertNewProduct(product: Product) {
        cartDao.upsertNewProduct(product)
    }

    override suspend fun updateExistingProduct(product: Product) {
        cartDao.updateExistingProduct(product)
    }

    override suspend fun deleteExistingProduct(product: Product) {
        cartDao.deleteExistingProduct(product)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return cartDao.getAllProducts()
    }

}