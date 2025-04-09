package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

@Dao
interface LocationDAO {
    @Query("select * from ${RMDatabase.LOCATION_TABLE}")
    fun getLocations(): Flow<List<LocationObject>>

    @Query("select * from ${RMDatabase.LOCATION_TABLE} where id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(episodes: List<LocationObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: LocationObject)
}