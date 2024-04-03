package com.example.testtask.cart_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testtask.cart_screen.data.remote.product.CartMeal

@Composable
fun CartMealElement(
    cartMeal: CartMeal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Blue)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxHeight()
                .fillMaxWidth(0.25f)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = cartMeal.strMealThumb,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}