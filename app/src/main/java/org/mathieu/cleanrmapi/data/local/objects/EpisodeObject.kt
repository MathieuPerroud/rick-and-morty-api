package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
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
internal class EpisodeObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var airDate: String = ""
    var episode: String = ""
    @MustBeCommaSeparatedIds
    var charactersIds: String = ""
    var created: String = ""
}

internal fun EpisodeResponse.toRealmObject() = EpisodeObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.airDate = air_date
    obj.episode = episode
    obj.charactersIds = characters.extractIdsFromUrls()
    obj.created = created
}

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)