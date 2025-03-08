package org.mathieu.cleanrmapi.domain.episode.models

/**
 * Represents a detailed description of a season.
 *
 * @property name The name of the season.
 * @property episodes The list of episodes contained in the season.
 * @property startedAt The first broadcast date of the season.
 * @property endedAt The end of broadcast of the season.
 */
data class Season(
    val name: String,
    val episodes: List<Episode>,
    val startedAt: String,
    val endedAt: String
)