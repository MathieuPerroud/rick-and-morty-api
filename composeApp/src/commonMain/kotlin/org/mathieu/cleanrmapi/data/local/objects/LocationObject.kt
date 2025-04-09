package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.location.Location
import org.mathieu.cleanrmapi.domain.location.LocationPreview


/**
 * Représente un lieu stocké en base de données.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
data class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String // Liste d'IDs séparés par des virgules
)

/**
 * Extensions pour convertir entre les modèles de domaine et les objets de base de données.
 */
fun LocationObject.toModel(residents: List<CharacterObject> = emptyList()): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents.map { it.toModel() }
    )
}

fun LocationObject.toPreviewModel(): LocationPreview {
    return LocationPreview(
        id = id,
        name = name
    )
}