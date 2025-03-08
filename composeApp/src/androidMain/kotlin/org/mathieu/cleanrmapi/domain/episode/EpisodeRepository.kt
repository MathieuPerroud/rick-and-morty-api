package org.mathieu.cleanrmapi.domain.episode

import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.models.Episode

interface EpisodeRepository {

    /**
     * Fetches the characters of a specific episode.
     *
     * @param episodeId The unique identifier of the episode.
     * @return Characters that plays in the specified episode.
     */
    suspend fun getCharactersIn(episodeId: Int): List<Character>


    /**
     * Fetches an episode based on the provided ID.
     *
     * @param id The unique identifier of the episode to be fetched.
     * @return Details of the specified episode.
     */
    suspend fun getEpisode(id: Int): Episode

}