package com.example.testtask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OfflineMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val ingredients: String,
    val cost: String,
    val category: String
)
