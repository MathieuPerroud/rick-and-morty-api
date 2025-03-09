package org.mathieu.cleanrmapi.data.repositories

import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository
import org.mathieu.cleanrmapi.domain.episode.models.Episode

class EpisodeRepositoryImpl: EpisodeRepository {
    override suspend fun getCharactersIn(episodeId: Int): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getEpisode(id: Int): Episode {
        TODO("Not yet implemented")
    }
}