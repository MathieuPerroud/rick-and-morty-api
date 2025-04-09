package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds

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
    val created: String
)