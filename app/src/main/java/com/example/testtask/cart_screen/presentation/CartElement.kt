package com.example.testtask.cart_screen.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.testtask.cart_screen.data.db.CartMeal
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CartElement(
    cartMeal: CartMeal,
    cartScreenVM: CartScreenVM,
    mainMealScreensVM: MainMealScreensVM,
    navController: NavHostController
) {

    //Delete cart meal if amount <= 0
    if(cartMeal.amount <= 0) {
        cartScreenVM.deleteCartMeal(cartMeal)
    }

    //Main row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                mainMealScreensVM.getMealByName(cartMeal.name)
                navController.navigate("meal_screen")
            }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .animateItemPlacement(),
    ) {
        //Box with image
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.25f)
                .aspectRatio(1f)
                .clip(CircleShape)
        ) {
            //Need to provide internet connection check here
            AsyncImage(
                model = cartMeal.image,
                contentDescription = "Product image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        //Column with name and increase/decrease buttons
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            //Box with name
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {
                Text(
                    text = cartMeal.name,
                    color = Color(0xff222831),
                    fontSize = 20.sp,
                )
            }

            //Row with buttons and amount
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Decrease button
                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        cartScreenVM.updateCartMeal(CartMeal(
                            name = cartMeal.name,
                            amount = cartMeal.amount - 1,
                            image = cartMeal.image
                        ))
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff5f5f5),
                        contentColor = Color(0xfffd3a69)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Minus icon"
                    )
                }

                //Amount
                Text(
                    text = cartMeal.amount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xff222831)
                )

                //Increase button
                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        cartScreenVM.updateCartMeal(CartMeal(
                            name = cartMeal.name,
                            amount = cartMeal.amount + 1,
                            image = cartMeal.image
                        ))
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff5f5f5),
                        contentColor = Color(0xfffd3a69)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Minus icon"
                    )
                }
            }
        }

        //Column with price
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "345 р.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xff222831)
                )
            }
        }
    }

    HorizontalDivider(thickness = 2.dp, color = Color(0xfff6f7f9))
}