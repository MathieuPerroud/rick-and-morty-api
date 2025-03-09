package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.domain.episode.models.Episode

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

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)