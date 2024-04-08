package com.example.foodies.auth.google_auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInErrorMessage: String? = null,
)
