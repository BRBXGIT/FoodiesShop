package com.example.foodies.main_meal_screens.data.dbs.category_db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Offline category entity
@Entity
data class OfflineCategory(
    @PrimaryKey
    val offlineCategory: String
)
