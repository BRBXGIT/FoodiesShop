package com.example.foodies.bottom_bar

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodies.auth.google_auth.GoogleAuthUiClient
import com.example.foodies.auth.presentation.SignInGoogleVM
import com.example.foodies.auth.presentation.login_screen.LoginScreen
import com.example.foodies.cart_screen.presentation.CartScreen
import com.example.foodies.cart_screen.presentation.CartScreenVM
import com.example.foodies.main_meal_screens.presentation.main_screen.MainScreen
import com.example.foodies.main_meal_screens.presentation.meal_screen.MealScreen
import com.example.foodies.auth.presentation.profile_screen.presentation.ProfileScreen
import com.example.foodies.auth.presentation.registration_screen.RegistrationScreen
import com.example.foodies.auth.presentation.SignInEmailVM
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient,
    preferencesManager: PreferencesManager
) {

    //Initialize systemUiController
    val systemUiController = rememberSystemUiController()

    //Initialize viewModels and navController
    val mainMealScreensVM = hiltViewModel<MainMealScreensVM>()
    val cartScreenVM = hiltViewModel<CartScreenVM>()
    val signInEmailVM = viewModel<SignInEmailVM>()
    val signInGoogleVM = viewModel<SignInGoogleVM>()

    val navController = rememberNavController()

    //Initialize coroutineScope and context
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //Changing start destination if user signed in
    var startDestination = "login_screen"
    if(signInEmailVM.getSignedInUser() != null) {
        startDestination = "main_screen"
    }

    //Take state from viewModel
    val state by signInGoogleVM.state.collectAsStateWithLifecycle()

    //Launch signInGoogle
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    signInGoogleVM.onSignInResult(signInResult)
                }
            }
        }
    )

    //Check if user signed in
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            navController.navigate("main_screen")
            signInGoogleVM.resetState()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        //Main screen composable
        composable(
            route = "main_screen",
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            MainScreen(
                mainMealScreensVM = mainMealScreensVM,
                navController = navController,
                systemUiController = systemUiController,
            )
        }

        //Profile screen composable
        composable(
            route = "profile_screen",
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            ProfileScreen(
                navController = navController,
                signInEmailVM = signInEmailVM,
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context, "Вы вышли из аккаунта", Toast.LENGTH_LONG
                        ).show()
                        navController.navigate("login_screen")
                    }
                },
                systemUiController = systemUiController,
                userData = googleAuthUiClient.getSignedInUser(),
                preferencesManager = preferencesManager
            )
        }

        //Cart screen composable
        composable(
            route = "cart_screen",
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            CartScreen(
                navController = navController,
                systemUiController = systemUiController,
                cartScreenVM = cartScreenVM,
                mainMealScreensVM = mainMealScreensVM
            )
        }

        //Meal screen composable
        composable(
            route = "meal_screen",
            enterTransition = { slideInHorizontally(
                initialOffsetX = { 500 },
                animationSpec = tween(
                    durationMillis = 500,
                )
            ) + fadeIn(tween(500)) },
            popExitTransition = { slideOutHorizontally(
                targetOffsetX = { 500 },
                animationSpec = tween(
                    durationMillis = 500
                )
            ) + fadeOut(tween(500)) },
        ) {
            MealScreen(
                navController = navController,
                mainMealScreensVM = mainMealScreensVM,
                systemUiController = systemUiController,
                cartScreenVM = cartScreenVM
            )
        }

        //Login screen composable
        composable(
            route = "login_screen",
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            LoginScreen(
                googleState = state,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signInWithGoogle()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                systemUiController = systemUiController,
                navController = navController,
                signInEmailVM = signInEmailVM,
                preferencesManager = preferencesManager
            )
        }

        composable(
            route = "registration_screen",
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            RegistrationScreen(
                systemUiController = systemUiController,
                navController = navController,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signInWithGoogle()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                signInEmailVM = signInEmailVM,
                preferencesManager = preferencesManager
            )
        }
    }
}