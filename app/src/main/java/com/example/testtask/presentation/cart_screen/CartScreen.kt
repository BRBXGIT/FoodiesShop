package com.example.testtask.presentation.cart_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Cart screen")
    }
}