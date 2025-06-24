package com.mercatto.myapplication.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.data.API.FakeStoreApiClient

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    private val api = FakeStoreApiClient.api

    suspend fun addProductToFirestore(product: Product) {
        productsCollection.document(product.id.toString()).set(product)
    }

    suspend fun fetchProducts(): List<Product> =
        api.getAllProducts()

    suspend fun fetchCategories(): List<String> =
        api.getAllCategories()
}
