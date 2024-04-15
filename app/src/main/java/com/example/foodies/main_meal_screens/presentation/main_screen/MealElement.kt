package com.example.foodies.main_meal_screens.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodies.auth.presentation.profile_screen.presentation.showToast
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM
import com.example.foodies.ui.theme.dimens

@SuppressLint("InvalidColorHexValue")
@Composable
fun MealElement(
    image: Any?,
    title: String,
    ingredients: String,
    navController: NavHostController,
    mainMealScreensVM: MainMealScreensVM,
) {

    val context = LocalContext.current

    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceTint)

    //Main row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.mealBoxHeight)
            .clickable {
                if (mainMealScreensVM.internetConnection) {
                    mainMealScreensVM.getMealByName(name = title)
                    navController.navigate("meal_screen")
                } else {
                    showToast(context, "Упс, нет интернета")
                }
            }
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //Box with image
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.45f)
                .aspectRatio(1f)
                .clip(CircleShape)
        ) {
            if(image != null) {
                AsyncImage(
                    model = image,
                    contentDescription = "Meal image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        //Column with title and ingredients
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = ingredients,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle.Default.copy(
                        lineBreak = LineBreak.Paragraph
                    )
                )
            }

            //Box with button
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp))
                        .height(40.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "от 345 р",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}