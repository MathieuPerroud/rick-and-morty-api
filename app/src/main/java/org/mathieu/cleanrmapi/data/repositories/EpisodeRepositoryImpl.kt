package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.common.extensions.toList
import org.mathieu.cleanrmapi.data.local.EpisodeLocal
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.local.objects.toRealmObject
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.EpisodeApi
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository
import org.mathieu.cleanrmapi.domain.episode.models.Episode

internal class EpisodeRepositoryImpl(
    private val characterApi: CharacterApi
) : EpisodeRepository {
    override suspend fun getCharactersIn(episodeId: Int): List<Character> {

        val episodeLocal = GetEpisodeObjectIfExists(episodeId)

        val idList = episodeLocal.charactersIds

        //TODO: fetch system

        return if (idList.contains(",")) {
            val characterResponse = characterApi.getCharactersFromIds(ids = idList)
            characterResponse.map { it.toRealmObject().toModel() }
        } else {

            val characterResponse = characterApi.getCharacter(idList.toInt())
            characterResponse?.toRealmObject()?.toModel()?.toList() ?: emptyList()
        }

    }

    override suspend fun getEpisode(id: Int): Episode {

        val episodeLocal = GetEpisodeObjectIfExists(episodeId = id)

        return episodeLocal.toModel()
    }

}

private object GetEpisodeObjectIfExists : KoinComponent {

    private val episodeApi: EpisodeApi by inject()
    private val episodeLocal: EpisodeLocal by inject()


    suspend operator fun invoke(episodeId: Int): EpisodeObject =
        tryToGetEpisodeLocally(episodeId)
            .fetchRemotelyIfNotFound(episodeId)
            .throwIfWeCannotFindIt()


    private suspend fun tryToGetEpisodeLocally(id: Int) = episodeLocal.getEpisode(id)

    private suspend fun EpisodeObject?.fetchRemotelyIfNotFound(id: Int): EpisodeObject? {
        if (this != null) return this

        return episodeApi.getEpisode(id = id)
            ?.toRealmObject()
            ?.also { obj ->
                episodeLocal.insert(obj)
            }
    }

    private fun EpisodeObject?.throwIfWeCannotFindIt(): EpisodeObject {
        if (this != null) return this
        throw Exception("Could not find Episode locally and remotely.")
    }

}
