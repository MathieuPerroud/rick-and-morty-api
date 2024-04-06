package org.mathieu.cleanrmapi.data.remote

import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    /**
     * Fetches a list of characters from the API.
     *
     * If the page parameter is not provided, it defaults to fetching the first page.
     *
     * @param page The page number to fetch. If null, the first page is fetched by default.
     * @return A paginated response containing a list of [CharacterResponse] for the specified page.
     */
    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int?): PaginatedResponse<CharacterResponse>

    /**
     * Fetches the details of a character with the given ID from the service.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [CharacterResponse] representing the details of the character.
     */
    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") id: Int): CharacterResponse?

}
