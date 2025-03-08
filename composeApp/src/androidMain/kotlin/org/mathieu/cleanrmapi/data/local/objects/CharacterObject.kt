package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.data.repositories.tryOrNull
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.character.models.CharacterDetails
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode

/**
 * Represents a character entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a character from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the character.
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
@Entity(tableName = RMDatabase.CHARACTER_TABLE)
class CharacterObject(
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
    @MustBeCommaSeparatedIds
    val episodesIds: String,
    val created: String
)

internal fun CharacterResponse.toDBObject() = CharacterObject(
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
    episodesIds = episode.extractIdsFromUrls(),
    created = created
)

internal suspend fun CharacterObject.toDetailedModel(
    idsToEpisodesConverter: suspend (episodesIds: String) -> List<Episode> = { emptyList() }
) = CharacterDetails(
    id = id,
    name = name,
    status = tryOrNull { CharacterStatus.valueOf(status) } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = tryOrNull { CharacterGender.valueOf(gender) } ?: CharacterGender.Unknown,
    origin = originName,
    location = locationName,
    avatarUrl = image,
    episodes = idsToEpisodesConverter(episodesIds)
)

internal fun CharacterObject.toModel() = Character(
    id = id,
    name = name,
    species = species,
    type = type,
    avatarUrl = image
)
