@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.foodies.auth.presentation.profile_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodies.R
import com.example.foodies.auth.google_auth.UserData
import com.example.foodies.auth.presentation.SignInEmailVM
import com.example.foodies.bottom_bar.presentation.BottomBar
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    signInEmailVM: SignInEmailVM,
    onSignOut: () -> Unit,
    userData: UserData?,
    systemUiController: SystemUiController
) {

    //Change colors of system bars
    SideEffect {
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
    }

    val user = if(signInEmailVM.getSignedInUser() != null) {
        User(
            profilePictureUrl = signInEmailVM.getSignedInUser()?.photoUrl.toString(),
            userName = signInEmailVM.getSignedInUser()?.displayName.toString()
        )
    } else {
        User(
            profilePictureUrl = userData?.profilePictureUrl.toString(),
            userName = userData?.userName.toString()
        )
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Профиль") },
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
        )  {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                ) {
                    if(user.profilePictureUrl != null) {
                        AsyncImage(
                            model = user.profilePictureUrl,
                            contentDescription = "Profile picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                HorizontalDivider(thickness = 2.dp, color = Color(0xfff6f7f9))

                ProfileElement(icon = R.drawable.ic_settings, section = "Настройки")
                ProfileElement(icon = R.drawable.ic_info, section = "Информация")
                ProfileElement(icon = R.drawable.ic_logout, section = "Выйти из аккаунта")
            }
        }
    }
}