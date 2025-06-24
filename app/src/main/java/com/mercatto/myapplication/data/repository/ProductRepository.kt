package com.mercatto.myapplication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.data.API.FakeStoreApiClient
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")
    private val auth = FirebaseAuth.getInstance()
    private val api = FakeStoreApiClient.api

    suspend fun addProductToFirestore(product: Product) {
        productsCollection.document(product.id).set(product).await()
    }

    suspend fun fetchProducts(): List<Product> =
        api.getAllProducts()

    suspend fun fetchUserProducts(): List<Product> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            val snapshot = productsCollection
                .whereEqualTo("ownerId", userId)
                .get()
                .await()
            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun fetchCategories(): List<String> =
        api.getAllCategories()
}
