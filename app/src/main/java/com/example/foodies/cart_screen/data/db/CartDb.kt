package com.example.foodies.cart_screen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

//Cart db
@Database(
    entities = [CartMeal::class],
    version = 1
)
abstract class CartDb: RoomDatabase() {

    abstract fun cartDao(): CartDao
}