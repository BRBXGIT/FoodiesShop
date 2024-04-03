package com.example.testtask.cart_screen.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity of product, i don't have api for cart
//that's why i decided to use local db
@Entity
data class Product(
    @PrimaryKey
    val name: String = "",
    val amount: Int = 1
)
