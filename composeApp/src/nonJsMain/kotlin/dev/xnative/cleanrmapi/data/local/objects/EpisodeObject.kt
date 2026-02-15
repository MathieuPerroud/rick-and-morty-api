package dev.xnative.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.xnative.cleanrmapi.data.extensions.extractIdsFromUrls
import dev.xnative.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import dev.xnative.cleanrmapi.data.local.RMDatabase
import dev.xnative.cleanrmapi.data.remote.responses.EpisodeResponse
import dev.xnative.cleanrmapi.domain.episode.models.Episode

/**
 *
 * Realm representation of an episode with only useful stored information gathered mainly
 * from remote source.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the episode.
 * @property airDate The broadcast date of the episode.
 * @property episode The episode code, which usually includes both the season and episode number (e.g., S01E01).
 * @property charactersIds Ids of the characters who appears in this episode.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
@Entity(tableName = RMDatabase.EPISODE_TABLE)
class EpisodeObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    @MustBeCommaSeparatedIds
    val charactersIds: String,
    val created: String
)

internal fun EpisodeResponse.toDBObject() = EpisodeObject(
    id = id,
    name = name,
    airDate = air_date,
    episode = episode,
    charactersIds = characters.extractIdsFromUrls(),
    created = created

)

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)