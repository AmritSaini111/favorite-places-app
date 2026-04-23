package com.example.favoriteplaces.data.repository

import com.example.favoriteplaces.data.local.PlaceDao
import com.example.favoriteplaces.data.local.PlaceEntity
import com.example.favoriteplaces.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomPlaceRepository(
    private val dao: PlaceDao
) : PlaceRepository {

    override fun getAllPlaces(): Flow<List<Place>> {
        return dao.getAllPlaces().map { list ->
            list.map { entity ->
                Place(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    address = entity.address,
                    createdAt = entity.createdAt,
                    imageUri = entity.imageUri,
                    latitude = entity.latitude,
                    longitude = entity.longitude,
                    isFavorite = entity.isFavorite,
                    rating = entity.rating
                )
            }
        }
    }

    override fun getPlaceById(id: Int): Flow<Place?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertPlace(place: Place) {
        dao.insertPlace(
            PlaceEntity(
                id = place.id,
                name = place.name,
                description = place.description,
                address = place.address,
                createdAt = place.createdAt,
                imageUri = place.imageUri,
                latitude = place.latitude,
                longitude = place.longitude,
                isFavorite = place.isFavorite,
                rating = place.rating
            )
        )
    }

    override suspend fun updatePlace(place: Place) {
        dao.updatePlace(
            PlaceEntity(
                id = place.id,
                name = place.name,
                description = place.description,
                address = place.address,
                createdAt = place.createdAt,
                imageUri = place.imageUri,
                latitude = place.latitude,
                longitude = place.longitude,
                isFavorite = place.isFavorite,
                rating = place.rating
            )
        )
    }

    override suspend fun deletePlace(place: Place) {
        dao.deletePlace(
            PlaceEntity(
                id = place.id,
                name = place.name,
                description = place.description,
                address = place.address,
                createdAt = place.createdAt,
                imageUri = place.imageUri,
                latitude = place.latitude,
                longitude = place.longitude,
                isFavorite = place.isFavorite,
                rating = place.rating
            )
        )
    }
}