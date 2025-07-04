package com.mercatto.myapplication.data.API


import com.mercatto.myapplication.data.model.Product
import retrofit2.http.GET

interface FakeStoreApi {
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>
}

object FakeStoreApiClient {
    val api: FakeStoreApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(FakeStoreApi::class.java)
    }
}
