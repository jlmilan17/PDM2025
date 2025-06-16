package com.mercatto.myapplication.data.repository


import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.data.API.FakeStoreApiClient

class ProductRepository {
    private val api = FakeStoreApiClient.api

    suspend fun fetchProducts(): List<Product> =
        api.getAllProducts()

    suspend fun fetchCategories(): List<String> =
        api.getAllCategories()
}
