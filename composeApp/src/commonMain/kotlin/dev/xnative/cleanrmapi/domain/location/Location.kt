package dev.xnative.cleanrmapi.domain.location

import dev.xnative.cleanrmapi.domain.character.models.Character


/**
 * Represents a specific location within a universe or dimension.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension or universe where this location exists.
 * @property residents A list of [Character] who have been known to reside or appear in this location.
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>
)