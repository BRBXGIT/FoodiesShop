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
    suspend fun upsertNewProduct(product: Product)

    @Update
    suspend fun updateExistingProduct(product: Product)

    @Delete
    suspend fun deleteExistingProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>
}