package com.example.foodies.auth.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CompletableDeferred

class SignInEmailVM: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createNewUserWithEmail(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    fun getSignedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun updateUserProfile() {
        val profileUpdates = userProfileChangeRequest {
            displayName = "BRBX"
            photoUri = Uri.parse("https://static.wikia.nocookie.net/shingekinokyojin/images/5/56/Bertholdt_Hoover_%28Anime%29_character_image.png/revision/latest?cb=20210109234137&path-prefix=ru")
        }

        firebaseAuth.currentUser?.updateProfile(profileUpdates)
    }

    fun signOutWithEmail() {
        firebaseAuth.signOut()
    }
}