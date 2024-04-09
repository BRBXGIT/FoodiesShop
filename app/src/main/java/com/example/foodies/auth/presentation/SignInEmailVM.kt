package com.example.foodies.auth.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
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

    fun getSignedInUser(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun signOutWithEmail() {
        firebaseAuth.signOut()
    }
}