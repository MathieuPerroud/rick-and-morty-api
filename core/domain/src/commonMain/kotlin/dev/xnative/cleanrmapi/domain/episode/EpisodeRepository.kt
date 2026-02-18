package dev.xnative.cleanrmapi.domain.episode

import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview

interface EpisodeRepository {

    /**
     * Fetches the characters of a specific episode.
     *
     * @param episodeId The unique identifier of the episode.
     * @return Characters that plays in the specified episode.
     */
    suspend fun getCharactersIn(episodeId: Int): List<CharacterPreview>


    /**
     * Fetches an episode based on the provided ID.
     *
     * @param id The unique identifier of the episode to be fetched.
     * @return Details of the specified episode.
     */
    suspend fun getEpisode(id: Int): EpisodePreview

}