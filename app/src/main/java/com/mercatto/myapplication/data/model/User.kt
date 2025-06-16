package com.mercatto.myapplication.data.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val isSeller: Boolean = false,
    val storeName: String? = null,
    val storeContact: String? = null,
    val storeLocation: String? = null,
    val storeImageUrl: String? = null
)