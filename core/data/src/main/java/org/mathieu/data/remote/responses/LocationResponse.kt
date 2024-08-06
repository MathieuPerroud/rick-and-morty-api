package org.mathieu.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents detailed information about a location, typically received from an API response.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension in which the location exists.
 * @property residents A list of characters who have last been seen or known to reside in this location.
 * @property url The unique URL endpoint specifically for this location.
 * @property created The timestamp indicating when the location was added to the database.
 */
@Serializable
internal data class LocationResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
)