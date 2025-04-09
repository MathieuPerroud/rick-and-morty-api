package org.mathieu.cleanrmapi.domain.location

/**
 * Représente une version simplifiée d'un lieu dans l'univers Rick&Morty.
 *
 * @property id L'identifiant unique du lieu.
 * @property name Le nom du lieu.
 */
data class LocationPreview(
    val id: Int,
    val name: String
)