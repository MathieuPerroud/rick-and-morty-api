package org.mathieu.cleanrmapi.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents detailed information about an episode, typically received from an API response.
 *
 * @property id The unique identifier of the episode.
 * @property name The name of the episode.
 * @property air_date The air date of the episode.
 * @property episode The code of the episode, representing its sequence in the series.
 * @property characters A list of URLs, each pointing to a character that appears in this episode.
 * @property url The URL to this episode's detail page on the API.
 * @property created The timestamp indicating when the episode data was created or first added to the database.
 */
@Serializable
internal data class EpisodeResponse (
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
