package dev.xnative.cleanrmapi.domain.character.models

import kotlinx.serialization.Serializable

/**
 * Represents a minimal description of a Rick&Morty character.
 *
 * @property id The unique identifier for the character.
 * @property name The name of the character.
 * @property species The species or classification of the character.
 * @property type Further description or subspecies of the character.
 * @property avatarUrl A URL pointing to an avatar or image of the character.
 */
@Serializable
data class CharacterPreview(
    override val id: Int,
    override val name: String,
    override val species: String,
    override val type: String,
    override val avatarUrl: String
) : Character

interface Character {
    val id: Int
    val name: String
    val species: String
    val type: String
    val avatarUrl: String
}