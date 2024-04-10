package com.example.foodies.auth.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
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

    suspend fun updateUserProfile(image: Uri, name: String): Boolean {
        val storageRef = FirebaseStorage.getInstance().reference.child("Users/${firebaseAuth.currentUser?.uid}/${image.lastPathSegment}")
        val upload = storageRef.putFile(image)

        val result = CompletableDeferred<Boolean>()
        upload.addOnCompleteListener {
            result.complete(it.isSuccessful)
        }

        storageRef.downloadUrl.addOnSuccessListener { uri ->
            val profileUpdates = userProfileChangeRequest {
                displayName = name
                photoUri = Uri.parse(uri.toString())
            }
            firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                result.complete(it.isSuccessful)
            }
        }

        return result.await()

    }

    fun signOutWithEmail() {
        firebaseAuth.signOut()
    }
}