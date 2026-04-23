package com.example.favoriteplaces.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places ORDER BY createdAt DESC")
    fun getAllPlaces(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE id = :id LIMIT 1")
    fun getPlaceById(id: Int): Flow<PlaceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    @Update
    suspend fun updatePlace(place: PlaceEntity)

    @Delete
    suspend fun deletePlace(place: PlaceEntity)
}
