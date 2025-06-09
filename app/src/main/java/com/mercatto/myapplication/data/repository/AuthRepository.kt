package com.mercatto.myapplication.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
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
            // 1) Crear usuario en Firebase Auth
            val authResult = auth
                .createUserWithEmailAndPassword(user.email, password)
                .await()

            val uid = authResult.user?.uid
                ?: throw IllegalStateException("UID de FirebaseAuth es null")

            // 2) Subir imagen de la tienda si se proporcionó una URI
            val imageUrl = storeImageUri?.let { uri ->
                val storageRef = storage.reference
                    .child("store_images/$uid.jpg")
                storageRef.putFile(uri).await()
                storageRef.downloadUrl.await().toString()
            }

            // 3) Guardar datos del usuario en Firestore
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
            // Opcional: podrías eliminar el usuario de Auth si falla el paso 3
            Result.failure(e)
        }
    }
}