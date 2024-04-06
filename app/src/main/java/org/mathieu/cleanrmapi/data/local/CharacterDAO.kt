package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject

@Dao
interface CharacterDAO {

    @Query("select * from ${RMDatabase.CHARACTER_TABLE}")
    fun getCharacters(): Flow<List<CharacterObject>>

    @Query("select * from ${RMDatabase.CHARACTER_TABLE} where id = :id")
    suspend fun getCharacter(id: Int): CharacterObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacters(characters: List<CharacterObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterObject)

}