package com.example.testtask.cart_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testtask.bottom_bar.presentation.BottomBar
import com.example.testtask.cart_screen.data.db.Product

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController,
    cartScreenVM: CartScreenVM
) {

    //Products from local db
    val products = cartScreenVM
        .getAllProductsFromDb()
        .collectAsState(initial = emptyList())
        .value


    if(products.isNotEmpty()) {
        LazyColumn {

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
                    color = Color(0xff222831),
                    fontSize = 18.sp,
                )

                Text(
                    text = "в каталоге :)",
                    color = Color(0xff222831),
                    fontSize = 18.sp
                )
            }
        }
    }
}