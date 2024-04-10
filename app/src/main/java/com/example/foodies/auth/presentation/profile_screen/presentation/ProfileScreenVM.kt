package com.example.foodies.auth.presentation.profile_screen.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    fun getSignedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    //Update user profile function
    suspend fun updateUserPicture(image: Uri, name: String): Boolean {
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
        }.addOnFailureListener {
            result.complete(false)
        }

        return result.await()
    }

    suspend fun updateUserName(image: String, name: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = Uri.parse(image)
        }

        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnSuccessListener {
            result.complete(true)
        }

        return result.await()
    }

    //Sign out function
    fun signOutWithEmail() {
        firebaseAuth.signOut()
    }
}