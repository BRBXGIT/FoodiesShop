package com.example.foodies.auth.presentation.profile_screen.data

import android.content.Context
import android.content.SharedPreferences

//This is used for saving what king of sign in method was used
class PreferencesManager(
    context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}