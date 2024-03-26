package com.example.testtask.data.db

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