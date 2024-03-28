package com.example.testtask.presentation.main_screen

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtask.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    visible: Boolean,
    mainScreenViewModel: MainScreenViewModel
) {
    //Main column
    Column(
        modifier = Modifier.background(Color(0xfffbfbfb))
    ) {
        //Row with qr scanner and city
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xfffbfbfb))
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Москва",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff000000)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "show more arrow icon",
                    tint = Color(0xff000000),
                    modifier = Modifier.size(14.dp),
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_qr),
                contentDescription = "qr icon",
                tint = Color(0xff000000),
                modifier = Modifier.size(28.dp)
            )

        }

        //Pager with banners
        val pagerState = rememberPagerState(pageCount = {
            2
        })
        //Yes, i created such effect with animated visibility.
        //I think it's a good way to implement it
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
                    .background(Color(0xfffbfbfb)),
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
        mainScreenViewModel.getCategories()
        val categories = mainScreenViewModel.categories.categories
        val offlineCategories = mainScreenViewModel.offlineCategories.collectAsState(
            initial = emptyList()
        ).value

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xfffbfbfb))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Check internet connection and load data from db if it's false
            if(mainScreenViewModel.internetConnection) {
                items(categories) { category ->
                    CategoryElement(
                        title = category.strCategory,
                        mainScreenViewModel = mainScreenViewModel
                    )
                }
            } else {
                items(offlineCategories) { category ->
                    CategoryElement(
                        title = category.offlineCategory,
                        mainScreenViewModel = mainScreenViewModel
                    )
                }
            }
        }

        Divider(thickness = 2.dp, color = Color(0xfff6f7f9))
    }
}