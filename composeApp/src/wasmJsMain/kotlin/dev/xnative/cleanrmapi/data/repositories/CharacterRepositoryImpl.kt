package dev.xnative.cleanrmapi.data.repositories

import dev.xnative.cleanrmapi.common.mapElement
import dev.xnative.cleanrmapi.common.toList
import dev.xnative.cleanrmapi.data.extensions.extractIdsFromUrls
import dev.xnative.cleanrmapi.data.local.DataStore
import dev.xnative.cleanrmapi.data.remote.CharacterApi
import dev.xnative.cleanrmapi.data.remote.EpisodeApi
import dev.xnative.cleanrmapi.data.remote.responses.CharacterResponse
import dev.xnative.cleanrmapi.data.remote.responses.toDetailedModel
import dev.xnative.cleanrmapi.data.remote.responses.toModel
import dev.xnative.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import kotlinx.coroutines.flow.Flow
import dev.xnative.cleanrmapi.domain.character.CharacterRepository
import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.domain.character.models.CharacterDetails
import dev.xnative.cleanrmapi.domain.episode.models.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

private const val nextPage = "next_characters_page_to_load"

internal class CharacterRepositoryImpl(
    private val dataStore: DataStore,
    private val episodeApi: EpisodeApi,
    private val characterApi: CharacterApi
): CharacterRepository {

    private val characters = MutableStateFlow<List<CharacterResponse>>(emptyList())

    override suspend fun getCharacters(): Flow<List<Character>> =
        characters
            .mapElement(CharacterResponse::toModel)
            .also { if (it.first().isEmpty()) fetchNext() }


    /**
     * Fetches the next batch of characters and saves them to local storage.
     *
     * This function works as follows:
     * 1. Reads the next page number from the data store.
     * 2. If there's a valid next page (i.e., page is not -1), it fetches characters from the API for that page.
     * 3. Extracts the next page number from the API response and updates the data store with it.
     * 4. Transforms the fetched character data into their corresponding realm objects.
     * 5. Saves the transformed realm objects to the local database.
     *
     * Note: If the `next` attribute from the API response is null or missing, the page number is set to -1, indicating there's no more data to fetch.
     */
    private suspend fun fetchNext() {

        val page: Int? = dataStore.retrieveFromInt(nextPage)

        if (page != -1) {

            val response = characterApi.getCharacters(page)

            val nextPageToLoad = response.info.next?.split("?page=")?.last()?.toInt() ?: -1

            dataStore.storeInto(nextPage, nextPageToLoad)

            characters.emit(characters.first() + response.results)
        }

    }


    override suspend fun loadMore() = fetchNext()


    /**
     * Retrieves the character with the specified ID.
     *
     * The function follows these steps:
     * 1. Tries to fetch the character from the local storage.
     * 2. If not found locally, it fetches the character from the API.
     * 3. Upon successful API retrieval, it saves the character to local storage.
     * 4. If the character is still not found, it throws an exception.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [Character] object representing the character details.
     * @throws Exception If the character cannot be found both locally and via the API.
     */
    override suspend fun getCharacterDetailed(id: Int): CharacterDetails {

        val characterResponse = GetCharacterObjectIfExists(characters = characters).invoke(id)

        return characterResponse.toDetailedModel(
            idsToEpisodesConverter = ::getEpisodesFromIdList
        )

    }

    override suspend fun getEpisodesWhere(characterId: Int): List<Episode> {
        val characterResponse = GetCharacterObjectIfExists(characters = characters).invoke(characterId)

        return getEpisodesFromIdList(idList = characterResponse.episode.extractIdsFromUrls())

    }

    private suspend fun getEpisodesFromIdList(@MustBeCommaSeparatedIds idList: String): List<Episode> {

        return if (idList.contains(",")) {
            val episodesResponse = episodeApi.getEpisodesFromIds(ids = idList)
            episodesResponse.map { it.toModel() }
        } else {
            val episodeResponse = episodeApi.getEpisode(idList.toInt())
            episodeResponse?.toModel()?.toList() ?: emptyList()
        }

    }

}
/**
 * Orchestrates the retrieval of a CharacterObject by attempting to fetch it locally first,
 * then remotely if it's not found in the local storage.
 *
 *  Note: By abstracting away part of the `getCharacter` repository implementation logic into a sequential,
 *  clearly-defined process, this approach aims to improve code readability and maintainability.
 *  It leverages the principle of separation of concerns, ensuring efficient data retrieval by minimizing
 *  network requests and providing a robust mechanism for error handling when character data cannot be found.
 *
 * @param characterId The unique identifier for the character to be retrieved. This ID is used first
 * to attempt to fetch the character from local storage and then from a remote source if necessary.
 *
 * @return A CharacterObject instance representing the character details. If the character is not found
 * locally, it is fetched from the remote API, converted into a realm object, and saved locally before
 * being returned.
 *
 * @throws Exception when the character cannot be found both locally and remotely.
 *
 */
private class GetCharacterObjectIfExists(private val characters: MutableStateFlow<List<CharacterResponse>>) : KoinComponent {

    private val characterApi: CharacterApi by inject()


    suspend operator fun invoke(characterId: Int): CharacterResponse =
        tryToGetCharacterLocally(characterId)
            .fetchRemotelyIfNotFound(characterId)
            .throwIfWeCannotFindIt()


    private suspend fun tryToGetCharacterLocally(id: Int) = characters.first().firstOrNull { it.id == id }

    private suspend fun CharacterResponse?.fetchRemotelyIfNotFound(id: Int): CharacterResponse? {
        if (this != null) return this

        return characterApi.getCharacter(id = id)
            ?.also { obj ->
                characters.emit(characters.first() + obj)
            }
    }

    private fun CharacterResponse?.throwIfWeCannotFindIt(): CharacterResponse {
        if (this != null) return this
        throw Exception("Could not find Character locally and remotely.")
    }

}
