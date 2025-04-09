package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 *
 * Représentation en base de données d'un lieu (Location) ne stockant que les informations essentielles
 * provenant principalement de la source distante.
 *
 * @property id Identifiant unique du lieu.
 * @property name Nom du lieu.
 * @property type Type du lieu (ex: planète, vaisseau...).
 * @property dimension Dimension dans laquelle se trouve le lieu.
 * @property residentsIds Liste des IDs des résidents de ce lieu, séparés par des virgules.
 * @property created Horodatage indiquant quand l'entité a été créée dans la base de données.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String,
    val url: String,
    val created: String
)

internal fun LocationResponse.toDBObject() = LocationObject(
    id = id,
    name = name,
    type= type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls(),
    url = url,
    created = created
)

internal fun LocationObject.toModel(residents: List<Character>) = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents
)