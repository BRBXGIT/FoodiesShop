package com.example.foodies.main_meal_screens.presentation.main_screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.auth.presentation.profile_screen.presentation.showToast
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    visible: Boolean,
    mainMealScreensVM: MainMealScreensVM,
    preferencesManager: PreferencesManager,
    scope: CoroutineScope,
    context: Context = LocalContext.current
) {
    //Main column
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    ) {
        //Row with qr scanner and city
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            CityDropDownMenu(
                preferencesManager = preferencesManager,
                scope = scope
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_qr),
                contentDescription = "qr icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(28.dp)
                    .noRippleClickable {
                        showToast(context, "https://www.youtube.com/watch?v=ok6-DndfAgU")
                    }
            )
        }

        //Pager with banners
        val pagerState = rememberPagerState(pageCount = {
            2
        })
        //Yes, i created such effect with animated visibility.
        //I think it's a good and easy way to implement it
        //Hope you will like it)
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(tween(700)) + expandVertically(tween(700)) + fadeIn(tween(500)),
            exit = slideOutVertically(tween(700)) + shrinkVertically(tween(700)) + fadeOut(tween(500))
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically,
                state = pagerState
            ) {

                val imagesList = listOf(
                    R.drawable.ad_banner_1,
                    R.drawable.ad_banner_2
                )

                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imagesList[pagerState.currentPage]),
                        contentDescription = "banner image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        //Get categories and add them to lazyRow
        mainMealScreensVM.getCategories()
        val categories = mainMealScreensVM.categories.categories
        val offlineCategories = mainMealScreensVM.offlineCategories.collectAsState(
            initial = emptyList()
        ).value

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Check internet connection and load data from db if it's false
            if(mainMealScreensVM.internetConnection) {
                items(categories) { category ->
                    CategoryElement(
                        title = category.strCategory,
                        mainMealScreensVM = mainMealScreensVM
                    )
                }
            } else {
                items(offlineCategories) { category ->
                    CategoryElement(
                        title = category.offlineCategory,
                        mainMealScreensVM = mainMealScreensVM
                    )
                }
            }
        }

        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceTint)
    }
}