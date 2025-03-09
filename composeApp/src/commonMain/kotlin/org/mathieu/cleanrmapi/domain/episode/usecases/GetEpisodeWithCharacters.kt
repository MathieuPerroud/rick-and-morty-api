package org.mathieu.cleanrmapi.domain.episode.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository
import org.mathieu.cleanrmapi.domain.episode.models.EpisodeWithCharacters

/**
 * Fetches the details of a specific episode based on the provided ID.
 *
 * @param episodeId The unique identifier of the episode to be fetched.
 * @return Episode listing characters that acts in it.
 */
object GetEpisodeWithCharacters : KoinComponent {
    // We could have done a function in the repository such as for CharacterDetails reach,
    // but there is another method that manages repositories results and work with only domain models
    // to build a new one.

    private val episodeRepository: EpisodeRepository by inject()

    suspend operator fun invoke(episodeId: Int): EpisodeWithCharacters {

        val episode = episodeRepository.getEpisode(episodeId)

        val characters = episodeRepository.getCharactersIn(episodeId)

        return EpisodeWithCharacters(
            episode = episode,
            characters = characters
        )

    }

}
