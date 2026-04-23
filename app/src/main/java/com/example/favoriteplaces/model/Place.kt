package com.example.favoriteplaces.model

data class Place(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val createdAt: Long = 0L,
    val imageUri: String? = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isFavorite: Boolean = false,
    val rating: Int = 0
)