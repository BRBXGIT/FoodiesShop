package com.example.testtask.cart_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testtask.bottom_bar.presentation.BottomBar
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun CartScreen(
    navController: NavHostController,
    cartScreenVM: CartScreenVM,
    systemUiController: SystemUiController
) {

    //Change colors of system bars
    SideEffect {
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
    }

    //Products from local db
    val products = cartScreenVM
        .getAllProductsFromDb()
        .collectAsState(initial = emptyList())
        .value

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        if(products.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xfffbfbfb))
                    .padding(PaddingValues(
                        bottom = innerPadding.calculateBottomPadding()
                    ))
            ) {

            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xfffbfbfb)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Пусто, выберите блюда",
                        color = Color(0xffa9aaad),
                        fontSize = 18.sp,
                    )

                    Text(
                        text = "в каталоге :)",
                        color = Color(0xffa9aaad),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}