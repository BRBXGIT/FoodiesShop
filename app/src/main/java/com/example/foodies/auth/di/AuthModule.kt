package com.example.foodies.auth.di

import android.content.Context
import com.example.foodies.auth.google_auth.GoogleAuthUiClient
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    //Provide googleAuthUiClient
    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext appContext: Context): GoogleAuthUiClient {
        return GoogleAuthUiClient(
            context = appContext,
            oneTapClient = Identity.getSignInClient(appContext)
        )
    }

    //Provide preferencesManager
    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext appContext: Context): PreferencesManager {
        return PreferencesManager(appContext)
    }

    //Provide firebaseAuth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}