package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.CharacterEntity

/**
 * Room access object exposing the queries the repository needs on the character table.
 *
 * This is the Room counterpart of the former Realm `CharacterLocal`: the same four
 * operations (list as [Flow], get by id, bulk save, single insert) with the same
 * presence semantics — [getCharacter] returns `null` when the row is absent, and both
 * inserts replace an existing row with the same primary key (the equivalent of Realm's
 * `UpdatePolicy.ALL`).
 */
@Dao
interface CharacterDao {

    /**
     * Emits the full character table and re-emits on every change to it,
     * mirroring the former Realm `query<CharacterObject>().find().asFlow()` stream.
     */
    @Query("select * from ${CharacterDatabase.CHARACTER_TABLE}")
    fun getCharacters(): Flow<List<CharacterEntity>>

    /**
     * Returns the character stored under [id], or `null` when no such row exists
     * (same absence contract as the former Realm `query(...).first().find()`).
     */
    @Query("select * from ${CharacterDatabase.CHARACTER_TABLE} where id = :id")
    suspend fun getCharacter(id: Int): CharacterEntity?

    /**
     * Persists every character of [characters], replacing rows that already exist
     * (same per-row replace semantics as the former `copyToRealm(..., UpdatePolicy.ALL)`).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacters(characters: List<CharacterEntity>)

    /**
     * Persists a single character, replacing an existing row with the same [CharacterEntity.id].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

}
