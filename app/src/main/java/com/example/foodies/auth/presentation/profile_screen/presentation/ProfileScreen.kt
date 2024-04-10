package com.example.foodies.auth.presentation.profile_screen.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodies.R
import com.example.foodies.auth.google_auth.UserData
import com.example.foodies.auth.presentation.SignInEmailVM
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.auth.presentation.profile_screen.data.User
import com.example.foodies.bottom_bar.presentation.BottomBar
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    signInEmailVM: SignInEmailVM,
    onSignOut: () -> Unit,
    userData: UserData?,
    systemUiController: SystemUiController,
    preferencesManager: PreferencesManager
) {

    //Change colors of system bars
    SideEffect {
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val signInWithGoogle = preferencesManager.getData("googleSignIn", false)

    val user = if(signInWithGoogle) {
        User(
            profilePictureUrl = userData?.profilePictureUrl,
            userName = userData?.userName
        )
    } else {
        User(
            profilePictureUrl = signInEmailVM.getSignedInUser()?.photoUrl.toString(),
            userName = signInEmailVM.getSignedInUser()?.displayName
        )
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            scope.launch {
                if(signInEmailVM.updateUserProfile(image = uri!!, name = "BRBX")) {
                    Toast.makeText(
                        context,
                        "Изменения сохранены",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    )

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
                        .border(width = 1.dp, color = Color(0xfffd3a69), shape = CircleShape)
                        .clickable {
                            if (signInWithGoogle) {
                                Toast.makeText(
                                        context,
                                        "Добавить фото можно только с аккаунта приложения",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            } else {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if((user.profilePictureUrl != null) && (user.profilePictureUrl != "null")) {
                        AsyncImage(
                            model = user.profilePictureUrl,
                            contentDescription = "Profile picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Add profile picture icon",
                            modifier = Modifier.size(50.dp),
                            tint = Color(0xfffd3a69)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalDivider(thickness = 2.dp, color = Color(0xfff6f7f9))

                ProfileElement(icon = R.drawable.ic_settings, section = "Настройки", navController = navController)
                ProfileElement(icon = R.drawable.ic_info, section = "Информация", navController = navController)
                ProfileElement(
                    icon = R.drawable.ic_logout,
                    section = "Выйти из аккаунта",
                    onSignOut = onSignOut,
                    signInWithGoogle = signInWithGoogle,
                    navController = navController
                )
            }
        }
    }
}