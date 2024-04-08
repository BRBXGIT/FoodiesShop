package com.example.foodies.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.foodies.auth.google_auth.SignInState

@Composable
fun AuthScreen(
    googleState: SignInState,
    onSignInClick: () -> Unit
) {

    //Sign in with Google
    val context = LocalContext.current
    LaunchedEffect(key1 = googleState.signInErrorMessage) {
        googleState.signInErrorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onSignInClick()
            }
        ) {
            Text(text = "Sign in")
        }
    }
}