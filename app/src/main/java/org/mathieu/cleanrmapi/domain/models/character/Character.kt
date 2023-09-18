package org.mathieu.cleanrmapi.domain.models.character

/**
 * Represents a detailed characterization, typically derived from a data source or API.
 *
 * @property id The unique identifier for the character.
 * @property name The name of the character.
 * @property status The current status of the character (Alive, Dead, or Unknown).
 * @property species The species or classification of the character.
 * @property type Further description or subspecies of the character.
 * @property gender The gender of the character.
 * @property origin The origin location of the character, represented as a name and an id of location.
 * @property location The current or last known location of the character, represented as a name and an id of location.
 * @property avatarUrl A URL pointing to an avatar or image of the character.
 */
data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: Pair<String, Int>,
    val location: Pair<String, Int>,
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
