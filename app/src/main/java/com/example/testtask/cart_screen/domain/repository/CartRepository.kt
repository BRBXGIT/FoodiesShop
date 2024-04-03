package com.example.testtask.cart_screen.domain.repository

import com.example.testtask.cart_screen.data.db.Product
import com.example.testtask.cart_screen.data.remote.product.ProductList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CartRepository {

    //Api functions
    suspend fun getProductByName(name: String): Response<ProductList>


    //Local db functions
    suspend fun upsertNewProduct(product: Product)

    suspend fun updateExistingProduct(product: Product)

    suspend fun deleteExistingProduct(product: Product)

    fun getAllProducts(): Flow<List<Product>>
}