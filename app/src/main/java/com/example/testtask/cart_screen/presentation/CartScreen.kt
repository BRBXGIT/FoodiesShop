package com.example.testtask.cart_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.testtask.bottom_bar.presentation.BottomBar
import com.google.accompanist.systemuicontroller.SystemUiController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController,
    systemUiController: SystemUiController
) {
    //Change colors of system bars
    SideEffect {
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {

    }
}