package com.example.testtask.presentation.main_screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtask.R

@Composable
fun BottomBar() {
    BottomAppBar(
        containerColor = Color(0xfff0f0f0),
        modifier = Modifier.height(50.dp)
    ) {
        //Row with icons and titles
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
                    tint = Color(0xfffd3a69),
                    modifier = Modifier.size(21.dp)
                    //I don't know why, but this icon slightly less others,
                    //That's why i gave 21.sp instead of 20.sp
                )
                Text(
                    text = "Меню",
                    fontSize = 14.sp,
                    color = Color(0xfffd3a69)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile icon",
                    tint = Color(0xff7b7b7b),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Профиль",
                    fontSize = 14.sp,
                    color = Color(0xff7b7b7b)
                )
            }


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Cart icon",
                    tint = Color(0xff7b7b7b),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Корзина",
                    fontSize = 14.sp,
                    color = Color(0xff7b7b7b)
                )
            }
        }
    }
}