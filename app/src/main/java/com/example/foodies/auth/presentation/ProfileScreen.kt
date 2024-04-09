package com.example.foodies.auth.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.foodies.bottom_bar.presentation.BottomBar
import kotlin.math.sign

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    signInEmailVM: SignInEmailVM
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    signInEmailVM.signOutWithEmail()
                    navController.navigate("login_screen")
                }
            ) {

            }
        }
    }
}