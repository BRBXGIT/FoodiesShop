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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.bottom_bar.presentation.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavHostController,
    context: Context
) {
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
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.shadow(4.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    PaddingValues(
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
                    color = MaterialTheme.colorScheme.surfaceTint,
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
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Stable 0.15.3 (12.04.2024 17:01)",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .clickable {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW, Uri.parse("https://github.com/BRBXGIT")
                                )
                            )
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Политика конфиденциальности",
                        color = MaterialTheme.colorScheme.primary,
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
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/BRBXGIT")
                                        )
                                    )
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_vk),
                            contentDescription = "VK icon",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW, Uri.parse("https://vk.com/brbxb")
                                        )
                                    )
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_telegram),
                            contentDescription = "Telegram icon",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://web.telegram.org/k/")
                                        )
                                    )
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_instagram),
                            contentDescription = "Instagram icon",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(30.dp)
                                .noRippleClickable {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://www.instagram.com/brbx16/")
                                        )
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}