package org.mathieu.cleanrmapi.data.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.character.models.CharacterDetails
import org.mathieu.cleanrmapi.domain.episode.models.Episode

class CharacterRepositoryImpl: CharacterRepository {
    override suspend fun getCharacters(): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadMore() {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterDetailed(id: Int): CharacterDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getEpisodesWhere(characterId: Int): List<Episode> {
        TODO("Not yet implemented")
    }
}