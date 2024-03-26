package com.example.testtask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

//Db for offline meals
@Database(
    entities = [OfflineMeal::class],
    version = 1
)
abstract class MealDB: RoomDatabase() {

    abstract fun MealDao(): MealDao
}