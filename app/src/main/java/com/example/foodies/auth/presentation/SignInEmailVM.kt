package com.example.foodies.auth.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CompletableDeferred

class SignInEmailVM: ViewModel() {

    //Initialize auth
    private val firebaseAuth = FirebaseAuth.getInstance()

    //Create new user
    suspend fun createNewUserWithEmail(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    //Sign in with email function
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

    //Update user profile function
    suspend fun updateUserProfile(image: Uri, name: String): Boolean {
        val storageRef = FirebaseStorage.getInstance().reference.child("Users/${firebaseAuth.currentUser?.uid}/${image.lastPathSegment}")
        val upload = storageRef.putFile(image)

        val result = CompletableDeferred<Boolean>()
        upload.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                    photoUri = Uri.parse(uri.toString())
                }
                firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnSuccessListener {
                    result.complete(true)
                }
            }
        }

        return result.await()
    }

    //Sign out function
    fun signOutWithEmail() {
        firebaseAuth.signOut()
    }
}