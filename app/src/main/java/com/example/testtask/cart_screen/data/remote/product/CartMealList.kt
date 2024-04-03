package com.example.testtask.cart_screen.data.remote.product

//Data class with all meals from api, again the same
data class CartMealList(
    //Val named meals to get right response from api
    val meals: List<CartMeal> = emptyList()
)
