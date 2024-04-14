package org.mathieu.cleanrmapi.data.local

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject

internal class CharacterLocal(private val database: RealmDatabase) {

    suspend fun getCharacters(): Flow<List<CharacterObject>> = database.getAll()

    suspend fun getCharacter(id: Int): CharacterObject? = database.getOneById(id)

    suspend fun saveCharacters(characters: List<CharacterObject>) = database.insert(characters)

    suspend fun insert(character: CharacterObject) = database.insert(character)

}
