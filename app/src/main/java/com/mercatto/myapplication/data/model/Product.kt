package com.mercatto.myapplication.data.model

data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val ownerId: String = ""
)
