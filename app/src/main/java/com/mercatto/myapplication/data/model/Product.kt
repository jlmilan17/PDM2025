package com.mercatto.myapplication.data.model

data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val ownerId: String = ""
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Product {
            val priceValue = map["price"]
            val price = when (priceValue) {
                is Long -> priceValue.toDouble()
                is Double -> priceValue
                else -> 0.0
            }

            return Product(
                id = map["id"] as? String ?: "",
                title = map["title"] as? String ?: "",
                price = price,
                description = map["description"] as? String ?: "",
                category = map["category"] as? String ?: "",
                image = map["image"] as? String ?: "",
                ownerId = map["ownerId"] as? String ?: ""
            )
        }
    }
}
