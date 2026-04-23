package com.example.favoriteplaces.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoriteplaces.model.Place

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val address: String,
    val createdAt: Long = System.currentTimeMillis(),
    val imageUri: String? = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isFavorite: Boolean = false,
    val rating: Int = 0
)

fun PlaceEntity.toPlace(): Place = Place(
    id = id,
    name = name,
    description = description,
    address = address,
    createdAt = createdAt,
    imageUri = imageUri,
    latitude = latitude,
    longitude = longitude,
    isFavorite = isFavorite,
    rating = rating
)

fun Place.toEntity(): PlaceEntity = PlaceEntity(
    id = id,
    name = name,
    description = description,
    address = address,
    createdAt = createdAt,
    imageUri = imageUri,
    latitude = latitude,
    longitude = longitude,
    isFavorite = isFavorite,
    rating = rating
)
