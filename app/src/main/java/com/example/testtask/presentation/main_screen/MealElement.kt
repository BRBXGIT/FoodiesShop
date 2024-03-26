package com.example.testtask.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@SuppressLint("InvalidColorHexValue")
@Composable
fun MealElement(
    image: Any,
    title: String,
    ingredients: String
) {
    Divider(thickness = 2.dp, color = Color(0xfff6f7f9))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.45f)
                .aspectRatio(1f)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Meal image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xff222831)
            )

            Text(
                text = ingredients,
                color = Color(0xffa9aaad),
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph
                )
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {  },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .border(1.dp, Color(0xfffd3a69), RoundedCornerShape(10.dp))
                        .height(40.dp)
                        .width(100.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff),
                        contentColor = Color(0xfffd3a69)
                    ),
                ) {
                    Text(
                        text = "от 345 р",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}