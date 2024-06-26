package com.example.foodies.main_meal_screens.data.dbs.meal_db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Offline meal entity
@Entity
data class OfflineMeal(
    @PrimaryKey
    val title: String,
    val ingredients: String,
    val cost: String,
    val category: String
)
