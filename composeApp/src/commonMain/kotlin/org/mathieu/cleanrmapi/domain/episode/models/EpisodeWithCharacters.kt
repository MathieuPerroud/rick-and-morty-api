package org.mathieu.cleanrmapi.domain.episode.models

import org.mathieu.cleanrmapi.domain.character.models.Character

/**
 * Represents a detailed description of an episode.
 *
 * @property id The unique identifier for the episode.
 * @property name The name of the episode.
 * @property airDate The broadcast date of the episode.
 * @property episode The episode code, which usually includes both the season and episode number (e.g., S01E01).
 * @property characters The characters that plays in this episode.
 */
data class EpisodeWithCharacters(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<Character>
) {
    constructor(episode: Episode, characters: List<Character>) : this(
        id = episode.id,
        name = episode.name,
        airDate = episode.airDate,
        episode = episode.episode,
        characters = characters
    )
}
