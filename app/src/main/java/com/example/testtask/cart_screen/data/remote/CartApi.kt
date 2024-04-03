package com.example.testtask.cart_screen.data.remote

import com.example.testtask.cart_screen.data.remote.product.CartMealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Api for cart, the same as the api for main screen
interface CartApi {

    @GET("/api/json/v1/1/search.php?")
    suspend fun getProductByName(@Query("s") name: String): Response<CartMealList>
}