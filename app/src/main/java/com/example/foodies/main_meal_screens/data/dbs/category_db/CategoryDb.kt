package com.example.foodies.main_meal_screens.data.dbs.category_db

import androidx.room.Database
import androidx.room.RoomDatabase

//Db for offline categories
@Database(
    entities = [OfflineCategory::class],
    version = 1
)
abstract class CategoryDb: RoomDatabase() {

    abstract fun CategoryDao(): CategoryDao
}