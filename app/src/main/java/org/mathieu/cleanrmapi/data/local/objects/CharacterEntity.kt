package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.CharacterDatabase
import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.data.repositories.tryOrNull
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.models.character.CharacterGender
import org.mathieu.cleanrmapi.domain.models.character.CharacterStatus

/**
 * Room entity of a character stored in the local database.
 *
 * Every persisted field and field name is carried over verbatim from the former Realm
 * `CharacterObject` (same presence semantics: all fields are non-null exactly as the
 * Realm object declared them). This type never leaves the data layer — mapping to and
 * from the domain [Character] is done by [toModel] / [toEntity] below.
 *
 * @property id Unique identifier of the character (primary key).
 * @property name Name of the character.
 * @property status Current status of the character (e.g. 'Alive', 'Dead').
 * @property species Biological species of the character.
 * @property type The type or subspecies of the character.
 * @property gender Gender of the character (e.g. 'Female', 'Male').
 * @property originName The origin location name.
 * @property originId The origin location id.
 * @property locationName The current location name.
 * @property locationId The current location id.
 * @property image URL pointing to the character's avatar image.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
@Entity(tableName = CharacterDatabase.CHARACTER_TABLE)
class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originId: Int,
    val locationName: String,
    val locationId: Int,
    val image: String,
    val created: String
)

/**
 * Maps the wire DTO [CharacterResponse] to the persisted [CharacterEntity].
 *
 * Field values are carried over exactly as the former `CharacterResponse.toRealmObject()`
 * did, including the `originId` / `locationId` fallbacks (`tryOrNull { ...url.split("/").last().toInt() } ?: -1`).
 */
internal fun CharacterResponse.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    originName = origin.name,
    originId = tryOrNull { origin.url.split("/").last().toInt() } ?: -1,
    locationName = location.name,
    locationId = tryOrNull { location.url.split("/").last().toInt() } ?: -1,
    image = image,
    created = created
)

/**
 * Maps the persisted [CharacterEntity] to the domain [Character].
 *
 * Same deterministic conversion as the former `CharacterObject.toModel()`: unknown
 * enum literals fall back to [CharacterStatus.Unknown] / [CharacterGender.Unknown],
 * origin and location stay a name-to-id `Pair`, and the avatar URL is the `image` field.
 */
internal fun CharacterEntity.toModel() = Character(
    id = id,
    name = name,
    status = tryOrNull { CharacterStatus.valueOf(status) } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = tryOrNull { CharacterGender.valueOf(gender) } ?: CharacterGender.Unknown,
    origin = originName to originId,
    location = locationName to locationId,
    avatarUrl = image
)
