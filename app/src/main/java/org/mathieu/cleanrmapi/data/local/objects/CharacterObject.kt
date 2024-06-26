package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.character.models.CharacterDetails
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
 * @property episodesIds Ids of the episodes where this character appeared.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
internal class CharacterObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var status: String = ""
    var species: String = ""
    var type: String = ""
    var gender: String = ""
    var originName: String = ""
    var originId: Int = -1
    var locationName: String = ""
    var locationId: Int = -1
    var image: String = ""
    @MustBeCommaSeparatedIds
    var episodesIds: String = ""
    var created: String = ""
}


internal fun CharacterResponse.toRealmObject() = CharacterObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.status = status
    obj.species = species
    obj.type = type
    obj.gender = gender
    obj.originName = origin.name
    obj.originId = tryOrNull { origin.url.split("/").last().toInt() } ?: -1
    obj.locationName = location.name
    obj.locationId = tryOrNull { location.url.split("/").last().toInt() } ?: -1
    obj.image = image
    obj.episodesIds = episode.extractIdsFromUrls()
    obj.created = created
}

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

private fun <T> tryOrNull(block: () -> T) = try {
    block()
} catch (_: Exception) {
    null
}
