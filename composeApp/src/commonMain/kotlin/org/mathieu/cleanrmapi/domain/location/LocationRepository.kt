package org.mathieu.cleanrmapi.domain.location

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.models.Location

interface LocationRepository {
    /**
     * Fetches the details of a specific location.
     *
     * @param locationId The unique identifier of the location to be fetched.
     * @return Details of the specified [Location].
     */
    suspend fun getLocation(locationId: Int): Location

    /**
     * Fetches the characters of a specific location.
     *
     * @param locationId The unique identifier of the location to be fetched.
     * @return Details of the specified [List<Character>].
     */
    suspend fun getCharactersIn(locationId: Int): List<Character>
}
