package org.mathieu.cleanrmapi.domain.character.models

import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.LocationPreview

/**
 * Represents a detailed description of a Rick&Morty character.
 *
 * @property id The unique identifier for the character.
 * @property name The name of the character.
 * @property episodes The episodes wheres this character plays.
 * @property status The current status of the character (Alive, Dead, or Unknown).
 * @property species The species or classification of the character.
 * @property type Further description or subspecies of the character.
 * @property gender The gender of the character.
 * @property origin The origin location of the character, represented as a name.
 * @property location The current or last known location of the character, represented as a name.
 * @property avatarUrl A URL pointing to an avatar or image of the character.
 */
data class CharacterDetails(
    val id: Int,
    val name: String,
    val episodes: List<Episode>,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: LocationPreview,
    val location: LocationPreview,
    val avatarUrl: String
)

/**
 * Describes the current state or condition of a character.
 */
enum class CharacterStatus {
    Alive, Dead, Unknown
}

/**
 * Represents the gender classification of a character.
 */
enum class CharacterGender {
    Female, Male, Genderless, Unknown
}
