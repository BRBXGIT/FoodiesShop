package com.example.foodies.cart_screen.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Cart meal
@Entity
data class CartMeal(
    @PrimaryKey
    val name: String,
    val amount: Int,
    val image: String
)
