package com.example.foodies.info_screen.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavHostController,
    systemUiController: SystemUiController,
    context: Context
) {

    SideEffect {
        systemUiController.setSystemBarsColor(Color(0xfffbfbfb))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Информация") },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Navigation icon",
                        modifier = Modifier
                            .noRippleClickable {
                                navController.popBackStack()
                            }
                            .padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xfffbfbfb),
                    titleContentColor = Color(0xff222831),
                    navigationIconContentColor = Color(0xfffd3a69)
                ),
                modifier = Modifier.shadow(4.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfffbfbfb))
                .padding(
                    PaddingValues(
                        bottom = innerPadding.calculateBottomPadding(),
                        top = innerPadding.calculateTopPadding()
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(0xfff6f7f9),
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Версия",
                        color = Color(0xff222831),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Stable 0.15.3 (12.04.2024 17:01)",
                        color = Color(0xffa9aaad),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .clickable {
                            context.startActivity(Intent(
                                Intent.ACTION_VIEW, Uri.parse("https://github.com/BRBXGIT")
                            ))
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Политика конфиденциальности",
                        color = Color(0xff222831),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_git),
                            contentDescription = "Git icon",
                            tint = Color(0xfffd3a69),
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(Intent(
                                        Intent.ACTION_VIEW, Uri.parse("https://github.com/BRBXGIT")
                                    ))
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_vk),
                            contentDescription = "VK icon",
                            tint = Color(0xfffd3a69),
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(Intent(
                                        Intent.ACTION_VIEW, Uri.parse("https://vk.com/brbxb")
                                    ))
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_telegram),
                            contentDescription = "Telegram icon",
                            tint = Color(0xfffd3a69),
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(Intent(
                                        Intent.ACTION_VIEW, Uri.parse("https://web.telegram.org/k/")
                                    ))
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_instagram),
                            contentDescription = "Instagram icon",
                            tint = Color(0xfffd3a69),
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(Intent(
                                        Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/brbx16/")
                                    ))
                                }
                        )
                    }
                }
            }
        }
    }
}