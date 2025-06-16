package com.mercatto.myapplication.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mercatto.myapplication.data.model.User
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

) {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(
        user: User,
        password: String,
        storeImageUri: Uri?
    ): Result<Unit> {
        return try {
            val authResult = auth
                .createUserWithEmailAndPassword(user.email, password)
                .await()

            val firebaseUser = authResult.user
                ?: throw IllegalStateException("UID de FirebaseAuth es null")

            val uid = firebaseUser.uid

            firebaseUser.updateProfile(
                userProfileChangeRequest {
                    displayName = user.fullName
                }
            ).await()

            val imageUrl = storeImageUri?.let { uri ->
                val storageRef = storage.reference
                    .child("store_images/$uid.jpg")
                storageRef.putFile(uri).await()
                storageRef.downloadUrl.await().toString()
            }

            val userToSave = user.copy(
                uid = uid,
                storeImageUrl = imageUrl
            )
            firestore
                .collection("users")
                .document(uid)
                .set(userToSave)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}