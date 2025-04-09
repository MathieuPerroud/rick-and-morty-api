package org.mathieu.cleanrmapi.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

interface LocationDAO {

    @Query("select * from ${RMDatabase.LOCATION_TABLE} where id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)

    @Query("select * from ${RMDatabase.LOCATION_TABLE} where id in (:ids)")
    suspend fun getLocations(ids: List<Int>): List<LocationObject>
}