package com.example.testtask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Offline category entity
@Entity
data class OfflineCategory(
    @PrimaryKey
    val offlineCategory: String
)
