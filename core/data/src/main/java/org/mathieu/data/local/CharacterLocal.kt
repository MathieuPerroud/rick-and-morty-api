package org.mathieu.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.data.local.objects.CharacterObject

internal class CharacterLocal(private val database: RealmDatabase) {

    suspend fun getCharacters(): Flow<List<CharacterObject>> = database.use {
        query<CharacterObject>().find().asFlow().map { it.list }
    }

    suspend fun getCharacter(id: Int): CharacterObject? = database.use {
        query<CharacterObject>("id == $id").first().find()
    }

    suspend fun saveCharacters(characters: List<CharacterObject>) = characters.onEach {
        insert(it)
    }

    suspend fun insert(character: CharacterObject) {
        database.write {
            copyToRealm(character, UpdatePolicy.ALL)
        }
    }

}