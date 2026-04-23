package com.example.favoriteplaces.data.repository

import com.example.favoriteplaces.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getAllPlaces(): Flow<List<Place>>
    fun getPlaceById(id: Int): Flow<Place?>
    suspend fun insertPlace(place: Place)
    suspend fun updatePlace(place: Place)
    suspend fun deletePlace(place: Place)
}
