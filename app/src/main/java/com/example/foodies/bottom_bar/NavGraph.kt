package com.example.foodies.bottom_bar

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodies.auth.google_auth.GoogleAuthUiClient
import com.example.foodies.auth.presentation.SignInGoogleVM
import com.example.foodies.auth.presentation.auth_screens.presentation.LoginScreen
import com.example.foodies.auth.presentation.auth_screens.presentation.RegistrationScreen
import com.example.foodies.auth.presentation.auth_screens.presentation.SignInEmailVM
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.example.foodies.auth.presentation.profile_screen.presentation.ProfileScreen
import com.example.foodies.auth.presentation.profile_screen.presentation.ProfileScreenVM
import com.example.foodies.cart_screen.presentation.CartScreen
import com.example.foodies.cart_screen.presentation.CartScreenVM
import com.example.foodies.info_screen.presentation.InfoScreen
import com.example.foodies.main_meal_screens.presentation.MainMealScreensVM
import com.example.foodies.main_meal_screens.presentation.main_screen.MainScreen
import com.example.foodies.main_meal_screens.presentation.meal_screen.MealScreen
import com.example.foodies.settings_screen.presentation.SettingsScreen
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
    val signInEmailVM = hiltViewModel<SignInEmailVM>()
    val profileScreenVM = hiltViewModel<ProfileScreenVM>()
    val signInGoogleVM = viewModel<SignInGoogleVM>()

    val navController = rememberNavController()

    //Initialize coroutineScope and context
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //Changing start destination if user signed in
    var startDestination = "login_screen"
    if(profileScreenVM.getSignedInUser() != null) {
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

    val darkTheme = preferencesManager.darkModeFlow.collectAsState(initial = null).value
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        //Main screen composable
        composable(
            route = "main_screen",
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) },
            popEnterTransition = { fadeIn(tween(400)) }
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background)

            MainScreen(
                mainMealScreensVM = mainMealScreensVM,
                navController = navController,
                preferencesManager = preferencesManager,
                scope = scope
            )
        }

        //Profile screen composable
        composable(
            route = "profile_screen",
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) },
            popEnterTransition = { fadeIn(tween(400)) }
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.tertiaryContainer)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background)

            ProfileScreen(
                navController = navController,
                profileScreenVM = profileScreenVM,
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        navController.navigate("login_screen")
                    }
                },
                userData = googleAuthUiClient.getSignedInUser(),
                preferencesManager = preferencesManager,
                context = context,
                scope = scope
            )
        }

        //Cart screen composable
        composable(
            route = "cart_screen",
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) },
            popEnterTransition = { fadeIn(tween(400)) }
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.tertiaryContainer)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background)

            CartScreen(
                navController = navController,
                cartScreenVM = cartScreenVM,
                mainMealScreensVM = mainMealScreensVM
            )
        }

        //Meal screen composable
        composable(
            route = "meal_screen",
            enterTransition = { fadeIn(tween(350)) },
            popExitTransition = { fadeOut(tween(350)) },
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.tertiaryContainer)

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
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surface)

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
                navController = navController,
                signInEmailVM = signInEmailVM,
                preferencesManager = preferencesManager
            )
        }

        composable(
            route = "registration_screen",
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surface)

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

        composable(
            route = "info_screen",
            enterTransition = { fadeIn(tween(400)) },
            popExitTransition = { fadeOut(tween(400)) },
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.tertiaryContainer)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surface)

            InfoScreen(
                navController = navController,
                context = context
            )
        }

        composable(
            route = "settings_screen",
            enterTransition = { fadeIn(tween(400)) },
            popExitTransition = { fadeOut(tween(400)) },
        ) {

            systemUiController.setStatusBarColor(MaterialTheme.colorScheme.tertiaryContainer)
            systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surface)

            SettingsScreen(
                navController = navController,
                preferencesManager = preferencesManager,
                scope = scope
            )
        }
    }
}