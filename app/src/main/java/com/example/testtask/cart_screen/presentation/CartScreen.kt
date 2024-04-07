package com.example.testtask.cart_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testtask.R
import com.example.testtask.bottom_bar.presentation.BottomBar
import com.example.testtask.bottom_bar.presentation.noRippleClickable
import com.example.testtask.cart_screen.data.db.CartMeal
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController,
    systemUiController: SystemUiController,
    cartScreenVM: CartScreenVM
) {
    //Change colors of system bars
    SideEffect {
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Корзина") },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Navigation icon",
                        modifier = Modifier
                            .noRippleClickable {
                                navController.popBackStack()
                            }
                            .padding(start = 16.dp)
                    )
                },
                actions = {
                    Icon(
                        tint = Color(0xfffd3a69),
                        painter = painterResource(id = R.drawable.ic_order),
                        contentDescription = "Action icon",
                        modifier = Modifier
                            .noRippleClickable {

                            }
                            .padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xfffbfbfb),
                    titleContentColor = Color(0xff222831),
                    navigationIconContentColor = Color(0xfffd3a69)
                ),
                modifier = Modifier.shadow(4.dp)
            )
        }
    ) { innerPadding ->

        val cartItems = cartScreenVM.getAllCartMeals().collectAsState(initial = emptyList()).value

        if(cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xfffbfbfb))
                    .padding(
                        PaddingValues(
                            bottom = innerPadding.calculateBottomPadding(),
                            top = innerPadding.calculateTopPadding()
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Пусто, выберите блюда", color = Color(0xffa9aaad))
                    Text(text = "в каталоге :)", color = Color(0xffa9aaad))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xfffbfbfb))
                    .padding(
                        PaddingValues(
                            bottom = innerPadding.calculateBottomPadding(),
                            top = innerPadding.calculateTopPadding()
                        )
                    )
            ) {
                items(cartItems) { cartMeal ->
                    CartElement(
                        cartMeal = cartMeal,
                        cartScreenVM = cartScreenVM
                    )
                }
            }
        }
    }
}