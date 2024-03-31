package com.example.testtask.presentation.main_screen.meal_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testtask.R
import com.example.testtask.presentation.main_screen.MainScreenViewModel

@Composable
fun MealScreen(
    mainScreenViewModel: MainScreenViewModel,
    navController: NavHostController
) {

    val meal = mainScreenViewModel.mealByName.meals[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfffbfbfb))
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(40.dp))
                .clip(RoundedCornerShape(40.dp))
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = "Meal photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color(0xfff3ccd6))
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = meal.strMeal,
                        color = Color(0xff222831),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "345 р.",
                        color = Color(0xffa9aaad),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MealDataRow(painter = R.drawable.ic_heart, text = "352")
                    MealDataRow(painter = R.drawable.ic_fire, text = "kcal")
                    MealDataRow(painter = R.drawable.ic_clock, text = "15-20 min")
                }
            }
        }
    }
}