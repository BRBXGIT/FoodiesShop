package com.example.testtask.main_meal_screens.presentation.main_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtask.main_meal_screens.presentation.MainMealScreensVM

@Composable
fun CategoryElement(
    title: String = "Beef",
    mainMealScreensVM: MainMealScreensVM
) {

    //Check if current category chosen
    val chosen = mainMealScreensVM.chosenCategory == title

    val chosenBoxColor by animateColorAsState(
        if (chosen) Color(0xfff3ccd6) else Color(0xffffffff),
        label = "animated color for box"
    )

    val chosenTextColor = if(chosen) {
        0xfffd3a69
    } else {
        0xffc3c4c9
    }

    //Category ui
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .shadow(2.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .drawBehind { drawRect(chosenBoxColor) }
            .clickable {
                mainMealScreensVM.chosenCategory = title
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color(chosenTextColor),
        )
    }
}