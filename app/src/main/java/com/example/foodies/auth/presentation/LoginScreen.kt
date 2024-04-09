package com.example.foodies.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.auth.google_auth.SignInState
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    googleState: SignInState,
    onSignInClick: () -> Unit,
    systemUiController: SystemUiController,
    navController: NavHostController,
    signInEmailVM: SignInEmailVM
) {

    //Change system bars color
    SideEffect {
        systemUiController.setSystemBarsColor(Color(0xfffbfbfb))
    }

    //Make toast if error
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = googleState.signInErrorMessage) {
        googleState.signInErrorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    //Main column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfffbfbfb))
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Box with Login text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Вход",
                color = Color(0xff222831),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(75.dp))

        //Column with textFields
        var password by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Почта") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFF897B0),
                    unfocusedBorderColor = Color(0xFFF897B0),
                    focusedLabelColor = Color(0xFFF897B0),
                    unfocusedLabelColor = Color(0xFFF897B0),
                    focusedTextColor = Color(0xff222831),
                    unfocusedTextColor = Color(0xff222831),
                    cursorColor = Color(0xff222831)
                )
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Пароль") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFF897B0),
                    unfocusedBorderColor = Color(0xFFF897B0),
                    focusedLabelColor = Color(0xFFF897B0),
                    unfocusedLabelColor = Color(0xFFF897B0),
                    focusedTextColor = Color(0xff222831),
                    unfocusedTextColor = Color(0xff222831),
                    cursorColor = Color(0xff222831)
                )
            )
        }

        Spacer(modifier = Modifier.height(75.dp))

        //Button to sign in
        Button(
            onClick = {
                if((password.isNotBlank()) && (email.isNotBlank())) {
                    scope.launch {
                        if(signInEmailVM.signInWithEmail(email, password)) {
                            Toast.makeText(context, "Вы успешно вошли", Toast.LENGTH_SHORT).show()
                            navController.navigate("main_screen")
                        } else {
                            Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Сначала заполните поля", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xfffd3a69),
                contentColor = Color(0xffffffff)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "Войти",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(37.5.dp))

        //Row with dividers and or text
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xffc3c4c9),
                modifier = Modifier.weight(0.45f)
            )

            Text(
                text = "ИЛИ",
                color = Color(0xffc3c4c9),
                fontSize = 15.sp
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xffc3c4c9),
                modifier = Modifier.weight(0.45f)
            )
        }

        Spacer(modifier = Modifier.height(37.5.dp))

        //Button for sign in with google
        Button(
            onClick = {
                onSignInClick()
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .border(1.dp, Color(0xfffd3a69), RoundedCornerShape(0.dp))
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Войдите с Google",
                    fontSize = 16.sp,
                    color = Color(0xfffd3a69),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        //Box with "don't have account" text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val registerText = buildAnnotatedString {
                withStyle(SpanStyle(
                    color = Color(0xFFF897B0),
                    fontSize = 16.sp,
                )) {
                    append("Новый пользователь? ")
                }
                pushStringAnnotation(tag = "register", annotation = "register")
                withStyle(SpanStyle(
                    color = Color(0xfffd3a69),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )) {
                    append("Регистрация")
                }
            }

            //Clickable text for register screen
            ClickableText(text = registerText, onClick = { offset ->
                registerText.getStringAnnotations(tag = "register", start = offset, end = offset).firstOrNull()
                    ?.let {
                        navController.navigate("registration_screen")
                    }
            })
        }
    }
}