package org.mathieu.cleanrmapi.domain.episode.models

/**
 * Represents a detailed description of an episode.
 *
 * @property id The unique identifier for the episode.
 * @property name The name of the episode.
 * @property airDate The broadcast date of the episode.
 * @property episode The episode code, which usually includes both the season and episode number (e.g., S01E01).
 */
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String
)
