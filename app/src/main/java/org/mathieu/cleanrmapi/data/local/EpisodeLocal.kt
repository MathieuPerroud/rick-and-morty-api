package org.mathieu.cleanrmapi.data.local

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject

internal class EpisodeLocal(private val database: RealmDatabase) {

    suspend fun getEpisodes(): Flow<List<EpisodeObject>> = database.getAll()

    suspend fun getEpisode(id: Int): EpisodeObject? = database.getOneById(id)

    suspend fun saveEpisodes(characters: List<EpisodeObject>) = database.insert(characters)

    suspend fun insert(character: EpisodeObject) = database.insert(character)

}
