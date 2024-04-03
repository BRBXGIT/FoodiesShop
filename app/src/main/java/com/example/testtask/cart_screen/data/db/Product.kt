package com.example.testtask.cart_screen.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val name: String,
    val amount: Int
)
