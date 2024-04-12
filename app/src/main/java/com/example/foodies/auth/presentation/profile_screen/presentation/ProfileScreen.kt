package com.example.foodies.auth.presentation.profile_screen.presentation

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodies.R
import com.example.foodies.auth.google_auth.UserData
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.auth.presentation.profile_screen.data.User
import com.example.foodies.bottom_bar.presentation.BottomBar
import com.example.foodies.bottom_bar.presentation.noRippleClickable
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileScreenVM: ProfileScreenVM,
    onSignOut: () -> Unit,
    userData: UserData?,
    systemUiController: SystemUiController,
    preferencesManager: PreferencesManager,
    context: Context,
    scope: CoroutineScope
) {

    //Change colors of system bars
    SideEffect {
        systemUiController.setStatusBarColor(Color(0xfffbfbfb))
        systemUiController.setNavigationBarColor(Color(0xfff0f0f0))
    }

    //Check if user sign in with google
    val signInWithGoogle = preferencesManager.getData("googleSignIn", false)

    var user by remember { mutableStateOf(
        if(signInWithGoogle) {
            User(
                profilePictureUrl = userData?.profilePictureUrl,
                userName = userData?.userName
            )
        } else {
            User(
                profilePictureUrl = profileScreenVM.getSignedInUser()?.photoUrl.toString(),
                userName = profileScreenVM.getSignedInUser()?.displayName
            )
        }
    ) }

    var name by rememberSaveable { mutableStateOf(
        if(user.userName == null) {
            "Пользователь"
        } else {
            user.userName
        }
    ) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        //Upload image to firebase and change profile picture
        onResult = { uri ->
            if(uri != null) {
                scope.launch {
                    if(profileScreenVM.updateUserPicture(image = uri, name = name.toString())) {
                        showToast(context, "Изменения сохранены")
                        user = User(
                            profilePictureUrl = profileScreenVM.getSignedInUser()?.photoUrl.toString(),
                            userName = profileScreenVM.getSignedInUser()?.displayName
                        )
                    } else {
                        showToast(context, "Что-то пошло не так")
                    }
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
        //Main column
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
            //Box with image
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color(0xfffd3a69), shape = CircleShape)
                        .clickable {
                            //If user sign in with google, he can't change profile picture'
                            if (signInWithGoogle) {
                                showToast(context, "Добавить фото можно только с аккаунта приложения")
                            } else {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    //Check is picture null
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

                val focusRequester = remember { FocusRequester() }
                val focusManager = LocalFocusManager.current
                TextField(
                    value = name.toString(),
                    onValueChange = { name = it },
                    modifier = Modifier
                        .width(150.dp)
                        .focusRequester(focusRequester),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xfffd3a69),
                        unfocusedBorderColor = Color(0xfffd3a69),
                        focusedLabelColor = Color(0xfffd3a69),
                        unfocusedLabelColor = Color(0xfffd3a69),
                        focusedTextColor = Color(0xff222831),
                        unfocusedTextColor = Color(0xff222831),
                        cursorColor = Color(0xff222831)
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        if (signInWithGoogle) {
                            showToast(context, "Изменить имя можно только с аккаунта приложения")
                        } else {
                            scope.launch {
                                if(profileScreenVM.updateUserName(image = user.profilePictureUrl.toString(), name = name.toString())) {
                                    showToast(context, "Изменения сохранены")
                                    user = User(
                                        profilePictureUrl = profileScreenVM.getSignedInUser()?.photoUrl.toString(),
                                        userName = profileScreenVM.getSignedInUser()?.displayName
                                    )
                                } else {
                                    showToast(context, "Что-то пошло не так")
                                }
                            }
                        }
                    })
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalDivider(thickness = 2.dp, color = Color(0xfff6f7f9))

                //Elements of column
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

fun showToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}