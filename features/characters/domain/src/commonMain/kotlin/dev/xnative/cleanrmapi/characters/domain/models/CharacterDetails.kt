package dev.xnative.cleanrmapi.characters.domain.models

import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview
import kotlinx.serialization.Serializable

/**
 * Represents a detailed description of a Rick&Morty character.
 *
 * @property id The unique identifier for the character.
 * @property name The name of the character.
 * @property episodes The episodes where this character plays.
 * @property status The current status of the character (Alive, Dead, or Unknown).
 * @property species The species or classification of the character.
 * @property type Further description or subspecies of the character.
 * @property gender The gender of the character.
 * @property origin The origin location of the character, represented as a name.
 * @property location The current or last known location of the character, represented as a name.
 * @property avatarUrl A URL pointing to an avatar or image of the character.
 */
@Serializable
data class CharacterDetails(
    override val id: Int,
    override val name: String,
    val episodes: List<EpisodePreview>,
    val status: CharacterStatus,
    override val species: String,
    override val type: String,
    val gender: CharacterGender,
    val origin: String,
    val location: String,
    override val avatarUrl: String
): Character

/**
 * Describes the current state or condition of a character.
 */
@Serializable
enum class CharacterStatus {
    Alive, Dead, Unknown
}

/**
 * Represents the gender classification of a character.
 */
@Serializable
enum class CharacterGender {
    Female, Male, Genderless, Unknown
}
