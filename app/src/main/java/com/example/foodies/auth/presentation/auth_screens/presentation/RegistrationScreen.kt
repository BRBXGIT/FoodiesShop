package com.example.foodies.auth.presentation.auth_screens.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.auth.presentation.profile_screen.presentation.showToast
import com.example.foodies.ui.theme.dimens
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    systemUiController: SystemUiController,
    navController: NavHostController,
    onSignInClick: () -> Unit,
    signInEmailVM: SignInEmailVM,
    preferencesManager: PreferencesManager
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    //Main column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Box with register text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Регистрация",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.logRegScreensUpSpacer))

        //Column with textFields
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
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
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.outline,
                    unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Пароль(минимум 6 символов)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.outline,
                    unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.logRegScreensUpSpacer))

        //Button to register in
        Button(
            onClick = {
                if((password.isNotBlank()) && (email.isNotBlank())) {
                    scope.launch {
                        if(signInEmailVM.createNewUserWithEmail(email, password)) {
                            showToast(context, "Регистрация успешна")
                            navController.navigate("login_screen")
                        } else {
                            showToast(context, "Ошибка при регистрации")
                        }
                    }
                } else {
                    showToast(context, "Сначала заполните поля")
                }
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "Зарегистрироваться",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.logRegScreensSpacer))

        //Row with dividers and or text
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.weight(0.45f)
            )

            Text(
                text = "ИЛИ",
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 15.sp
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.weight(0.45f)
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.logRegScreensSpacer))

        //Button for sign in with google
        Button(
            onClick = {
                onSignInClick()
                scope.launch { preferencesManager.storeGoogleSignIn(true) }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(0.dp))
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
                    color =  MaterialTheme.colorScheme.tertiary,
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
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 16.sp,
                )) {
                    append("Уже зарегистрированы? ")
                }
                pushStringAnnotation(tag = "login", annotation = "login")
                withStyle(SpanStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )) {
                    append("Вход")
                }
            }

            //Clickable text for login screen
            ClickableText(text = registerText, onClick = { offset ->
                registerText.getStringAnnotations(tag = "login", start = offset, end = offset).firstOrNull()
                    ?.let {
                        navController.navigate("login_screen")
                    }
            })
        }
    }
}