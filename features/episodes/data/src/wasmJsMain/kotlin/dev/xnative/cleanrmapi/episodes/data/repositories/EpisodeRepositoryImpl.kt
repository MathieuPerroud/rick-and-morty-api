package dev.xnative.cleanrmapi.episodes.data.repositories

import dev.xnative.cleanrmapi.data.extensions.extractIdsFromUrls
import dev.xnative.cleanrmapi.data.remote.CharacterApi
import dev.xnative.cleanrmapi.data.remote.EpisodeApi
import dev.xnative.cleanrmapi.data.remote.responses.EpisodeResponse
import dev.xnative.cleanrmapi.data.remote.responses.toPreview
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.domain.episode.EpisodeRepository
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class EpisodeRepositoryImpl(
    private val characterApi: CharacterApi
) : EpisodeRepository {

    private val episodes = MutableStateFlow<List<EpisodeResponse>>(emptyList())

    override suspend fun getCharactersIn(episodeId: Int): List<CharacterPreview> {

        val episodeLocal = GetEpisodeObjectIfExists(episodes = episodes).invoke(episodeId)

        val idList = episodeLocal.characters.extractIdsFromUrls()

        return if (idList.contains(",")) {
            val characterResponse = characterApi.getCharactersFromIds(ids = idList)
            characterResponse.map { it.toPreview() }
        } else {

            val characterResponse = characterApi.getCharacter(idList.toInt())
            characterResponse?.toPreview()?.toList() ?: emptyList()
        }

    }

    override suspend fun getEpisode(id: Int): EpisodePreview {

        val episodeResponse = GetEpisodeObjectIfExists(episodes = episodes).invoke(episodeId = id)

        return episodeResponse.toPreview()
    }

}

private class GetEpisodeObjectIfExists(private val episodes: MutableStateFlow<List<EpisodeResponse>>) : KoinComponent {

    private val episodeApi: EpisodeApi by inject()

    suspend operator fun invoke(episodeId: Int): EpisodeResponse =
        tryToGetEpisodeLocally(episodeId)
            .fetchRemotelyIfNotFound(episodeId)
            .throwIfWeCannotFindIt()


    private suspend fun tryToGetEpisodeLocally(id: Int) = episodes.first().firstOrNull { it.id == id }

    private suspend fun EpisodeResponse?.fetchRemotelyIfNotFound(id: Int): EpisodeResponse? {
        if (this != null) return this

        return episodeApi.getEpisode(id = id)
            ?.also { obj ->
                episodes.emit(episodes.first() + obj)
            }
    }

    private fun EpisodeResponse?.throwIfWeCannotFindIt(): EpisodeResponse {
        if (this != null) return this
        throw Exception("Could not find Episode locally and remotely.")
    }

}
