package com.example.testtask.cart_screen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

//Cart db
@Database(
    entities = [Product::class],
    version = 1
)
abstract class CartDb: RoomDatabase() {

    abstract fun CartDao(): CartDao
}