package com.example.foodies.auth.presentation.profile_screen.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//This is used for saving what king of sign in method was used
class PreferencesManager(
    private val context: Context
) {

    // Define a DataStore for user preferences
    private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "foodies_preferences")

    // Define a key for the dark mode
    private val DARK_MODE_KEY = stringPreferencesKey("dark_mode")

    // Function to store the toggle value
    suspend fun storeDarkMode(isOn: String) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isOn
        }
    }

    // Function to retrieve the dark mode value
    val darkModeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY]
    }

    private val SIGN_IN_WITH_GOOGLE_KEY = booleanPreferencesKey("sign_in_with_google")
    suspend fun storeGoogleSignIn(isOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SIGN_IN_WITH_GOOGLE_KEY] = isOn
        }
    }

    // Function to retrieve the "google sign in" value
    val googleSighIn: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[SIGN_IN_WITH_GOOGLE_KEY]
    }
}