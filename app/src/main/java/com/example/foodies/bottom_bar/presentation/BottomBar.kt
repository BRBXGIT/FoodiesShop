package com.example.foodies.bottom_bar.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodies.R

@Composable
fun BottomBar(
    navController: NavHostController
) {

    //Colors for active and basic destinations
    val activeIconColor by remember { mutableStateOf(Color(0xfffd3a69)) }
    val basicIconColor by remember { mutableStateOf(Color(0xff7b7b7b)) }
    val currentDestination = navController.currentDestination!!.route

    BottomAppBar(
        containerColor = Color(0xfff0f0f0),
        modifier = Modifier.height(54.dp),
    ) {
        //Row with icons and titles
        //Need to change to normal bottom bar, but not now)
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menu icon",
                    tint = if(currentDestination == "main_screen") activeIconColor else basicIconColor,
                    modifier = Modifier
                        .size(21.dp)
                        .noRippleClickable {
                            if(currentDestination != "main_screen") {
                                navController.navigate("main_screen")
                            }
                        }
                    //I don't know why, but this icon slightly less others,
                    //That's why i gave 21.sp instead of 20.sp
                )
                Text(
                    text = "Меню",
                    fontSize = 14.sp,
                    color = if(currentDestination == "main_screen") activeIconColor else basicIconColor,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile icon",
                    tint = if(currentDestination == "profile_screen") activeIconColor else basicIconColor,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable {
                            if(currentDestination != "profile_screen") {
                                navController.navigate("profile_screen")
                            }
                        }
                )
                Text(
                    text = "Профиль",
                    fontSize = 14.sp,
                    color = if(currentDestination == "profile_screen") activeIconColor else basicIconColor,
                )
            }


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Cart icon",
                    tint = if(currentDestination == "cart_screen") activeIconColor else basicIconColor,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable {
                            if(currentDestination != "cart_screen") {
                                navController.navigate("cart_screen")
                            }
                        }
                )
                Text(
                    text = "Корзина",
                    fontSize = 14.sp,
                    color = if(currentDestination == "cart_screen") activeIconColor else basicIconColor,
                )
            }
        }
    }
}

//Modifier extension for clicking without ripple
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}