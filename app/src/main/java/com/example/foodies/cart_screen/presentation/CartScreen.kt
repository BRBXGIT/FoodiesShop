package com.example.foodies.cart_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.auth.presentation.profile_screen.presentation.showToast
import com.example.foodies.basic_top_bar.presentation.BasicTopBar
import com.example.foodies.bottom_bar.presentation.BottomBar
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavHostController,
    cartScreenVM: CartScreenVM,
    mainMealScreensVM: MainMealScreensVM
) {
    val cartItems = cartScreenVM.getAllCartMeals().collectAsState(initial = emptyList()).value
    val context = LocalContext.current

    val lazyColumnState = rememberLazyListState()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
            BasicTopBar(
                navController = navController,
                title = "Корзина",
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Заказать", fontWeight = FontWeight.Bold) },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.ic_order),
                    contentDescription = "Check icon"
                ) },
                onClick = {
                    if (cartItems.isEmpty()) {
                        showToast(context, "Сначала что-нибудь добавьте")
                    } else {
                        showToast(context, "У меня нет бизнеса что-бы ты что-нибудь заказал")
                    }
                },
                containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp),
                expanded = !lazyColumnState.canScrollBackward,
                modifier = Modifier.height(55.dp)
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
                    ),
                state = lazyColumnState
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