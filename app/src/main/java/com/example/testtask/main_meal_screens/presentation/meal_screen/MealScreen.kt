package com.example.testtask.main_meal_screens.presentation.meal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testtask.R
import com.example.testtask.bottom_bar.presentation.noRippleClickable
import com.example.testtask.cart_screen.data.db.CartMeal
import com.example.testtask.cart_screen.presentation.CartScreenVM
import com.example.testtask.main_meal_screens.data.remote.meal.Meal
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlin.reflect.full.memberProperties

@Composable
fun MealScreen(
    mainMealScreensVM: MainMealScreensVM,
    navController: NavHostController,
    systemUiController: SystemUiController,
    cartScreenVM: CartScreenVM
) {

    SideEffect {
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
        systemUiController.setNavigationBarColor(Color(0xffffffff))
    }

    val meal = mainMealScreensVM.mealByName.meals[0]

    //Iterate over data class and add values to ingredients
    var ingredients = ""
    for(ingredient in Meal::class.memberProperties) {
        if(ingredient.name.take(13) == "strIngredient") {
            if(ingredient.get(meal) != "") {
                ingredients += if(ingredients.isNotEmpty()) {
                    "${ingredient.get(meal)}, ".lowercase()
                } else {
                    "${ingredient.get(meal)}, "
                }
            }
        }
    }
    ingredients = ingredients.dropLast(2)

    //Iterate over data class again
    val measures = mutableListOf<String>()
    for(measure in Meal::class.memberProperties) {
        if(measure.name.take(10) == "strMeasure") {
            if((measure.get(meal) != " ") && (measure.get(meal) != "")) {
                measures += measure.get(meal).toString()
            }
        }
    }


    cartScreenVM.checkIsMealInCart(meal.idMeal)

    //I used scaffold to create bottom add to cart button
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(70.dp),
                containerColor = Color(0xffffffff)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    Button(
                        onClick = {
                            cartScreenVM.upsertNewMealToCart(CartMeal(
                                name = meal.strMeal,
                                amount = 1,
                                image = meal.strMealThumb
                            ))
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xfffd3a69),
                            contentColor = Color(0xffffffff)
                        )
                    ) {
                        Text(
                            text = "",
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        //Main column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfffbfbfb)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Box with image
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if(meal.strMealThumb != "") {
                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = "Meal image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Icon go back",
                    tint = Color(0xff000000),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 8.dp)
                        .noRippleClickable { navController.popBackStack() }
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            //Column with text and ingredients
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = meal.strMeal,
                    color = Color(0xff222831),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = ingredients,
                    color = Color(0xffa9aaad),
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Lazy column with ingredients and measures
            val ingredientsList = ingredients.split(", ")
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Divider(thickness = 2.dp, color = Color(0xfff6f7f9))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            PaddingValues(
                                bottom = innerPadding.calculateBottomPadding()
                            )
                        ),
                ) {
                    itemsIndexed(measures) { index, measure ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(start = 16.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ingredientsList[index].lowercase(),
                                color = Color(0xffa9aaad),
                                fontSize = 17.sp
                            )

                            Text(
                                text = measure,
                                color = Color(0xff222831),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Divider(thickness = 2.dp, color = Color(0xfff6f7f9))

                    }
                }
            }
        }
    }
}