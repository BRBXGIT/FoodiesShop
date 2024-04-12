package com.example.foodies.cart_screen.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.bottom_bar.presentation.BottomBar
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController,
    systemUiController: SystemUiController,
    cartScreenVM: CartScreenVM,
    mainMealScreensVM: MainMealScreensVM
) {
    val cartItems = cartScreenVM.getAllCartMeals().collectAsState(initial = emptyList()).value
    val context = LocalContext.current

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
                        tint = MaterialTheme.colorScheme.tertiary,
                        painter = painterResource(id = R.drawable.ic_order),
                        contentDescription = "Action icon",
                        modifier = Modifier
                            .noRippleClickable {
                                if(cartItems.isEmpty()) {
                                    Toast.makeText(context, "Сначала что-нибудь добавьте :)", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Для такого у меня апи нет, хотя возможно я подключу FireBase", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.shadow(4.dp)
            )
        }
    ) { innerPadding ->
        if(cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
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
                    Text(text = "Пусто, выберите блюда", color = MaterialTheme.colorScheme.secondary)
                    Text(text = "в каталоге :)", color = MaterialTheme.colorScheme.secondary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(
                        PaddingValues(
                            bottom = innerPadding.calculateBottomPadding(),
                            top = innerPadding.calculateTopPadding()
                        )
                    )
            ) {
                items(cartItems, key = { cartMeal -> cartMeal.name }) { cartMeal ->
                    CartElement(
                        cartMeal = cartMeal,
                        cartScreenVM = cartScreenVM,
                        mainMealScreensVM = mainMealScreensVM,
                        navController = navController
                    )
                }
            }
        }
    }
}