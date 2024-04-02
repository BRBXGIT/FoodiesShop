package com.example.testtask.cart_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.testtask.bottom_bar.presentation.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Cart screen")
        }
    }
}