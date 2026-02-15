package dev.xnative.cleanrmapi.domain.character

import kotlinx.coroutines.flow.Flow
import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.domain.character.models.CharacterDetails
import dev.xnative.cleanrmapi.domain.episode.models.Episode

interface CharacterRepository {
    /**
     * Fetches a list of characters from the data source. The function streams the results
     * as a [Flow] of [List] of [Character] objects.
     *
     * @return A flow emitting a list of characters.
     */
    suspend fun getCharacters(): Flow<List<Character>>

    /**
     * Loads more characters from the data source, usually used for pagination purposes.
     * This function typically fetches the next set of characters and appends them to the existing list.
     */
    suspend fun loadMore()

    /**
     * Fetches the details of a specific character based on the provided ID.
     *
     * @param id The unique identifier of the character to be fetched.
     * @return Details of the specified character.
     */
    suspend fun getCharacterDetailed(id: Int): CharacterDetails

    /**
     * Fetches the episodes of a specific character.
     *
     * @param characterId The unique identifier of the character.
     * @return Episodes where acts the specified character.
     */
    suspend fun getEpisodesWhere(characterId: Int): List<Episode>

}