package org.mathieu.cleanrmapi.domain.location


interface LocationRepository {
    /**
     * Récupère un lieu selon l'ID fourni.
     *
     * @param id L'identifiant unique du lieu à récupérer.
     * @return Les détails du lieu spécifié.
     */
    suspend fun getLocation(id: Int): Location

    /**
     * Récupère une version simplifiée d'un lieu selon l'ID fourni.
     *
     * @param id L'identifiant unique du lieu à récupérer.
     * @return Une version simplifiée du lieu spécifié.
     */
    suspend fun getLocationPreview(id: Int): LocationPreview
}