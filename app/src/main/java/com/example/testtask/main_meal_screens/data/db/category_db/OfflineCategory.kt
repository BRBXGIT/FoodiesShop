package com.example.testtask.main_meal_screens.data.db.category_db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Offline category entity
@Entity
data class OfflineCategory(
    @PrimaryKey
    val offlineCategory: String
)
