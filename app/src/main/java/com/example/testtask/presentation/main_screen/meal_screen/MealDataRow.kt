package com.example.testtask.presentation.main_screen.meal_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MealDataRow(
    painter: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = painter),
            contentDescription = "Some icon",
            tint = Color(0xfffd3a69),
            modifier = Modifier.size(30.dp)
        )

        Text(
            text = text,
            color = Color(0xffa9a9ac),
            fontSize = 19.sp
        )
    }
}