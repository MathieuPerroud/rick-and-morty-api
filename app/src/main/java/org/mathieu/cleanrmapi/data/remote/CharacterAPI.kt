package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse
import org.mathieu.cleanrmapi.data.validators.IdListValidator
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds


internal class CharacterApi(private val client: HttpClient) {

    /**
     * Fetches a list of characters from the API.
     *
     * If the page parameter is not provided, it defaults to fetching the first page.
     *
     * @param page The page number to fetch. If null, the first page is fetched by default.
     * @return A paginated response containing a list of [CharacterResponse] for the specified page.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getCharacters(page: Int?): PaginatedResponse<CharacterResponse> = client
        .get("character/") {
            if (page != null)
                url {
                    parameter("page", page)
                }
        }
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches the details of a character with the given ID from the service.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [CharacterResponse] representing the details of the character.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getCharacter(id: Int): CharacterResponse? = client
        .get("character/$id")
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Retrieves multiple characters by their IDs.
     *
     * This function accepts a list of character IDs (`ids`) and makes an API call to fetch the corresponding
     * characters. The API expects a string of IDs separated by commas, such as "/character/1,2,3".
     *
     * @param ids A list of characters IDs to be retrieved.
     * @return A paginated response containing the requested characters as `EpisodeResponse`.
     *
     * @throws Exception if the request fails or the response status is not `HttpStatusCode.OK`.
     */
    suspend fun getCharactersFromIds(@MustBeCommaSeparatedIds ids: String): List<CharacterResponse> {

        IdListValidator.assertValid(ids)

        return client
            .get("character/$ids")
            .accept(HttpStatusCode.OK)
            .body()
    }


}
