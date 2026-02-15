package dev.xnative.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import dev.xnative.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import dev.xnative.cleanrmapi.data.remote.responses.EpisodeResponse
import dev.xnative.cleanrmapi.data.validators.IdListValidator

internal class EpisodeApi(private val client: HttpClient) {

    /**
     * Retrieves multiple episodes by their IDs.
     *
     * This function accepts a list of episode IDs (`ids`) and makes an API call to fetch the corresponding
     * episodes. The API expects a string of IDs separated by commas, such as "/episode/1,2,3".
     *
     * @param ids A list of episode IDs to be retrieved.
     * @return A paginated response containing the requested episodes as `EpisodeResponse`.
     *
     * @throws Exception if the request fails or the response status is not `HttpStatusCode.OK`.
     */
    suspend fun getEpisodesFromIds(@MustBeCommaSeparatedIds ids: String): List<EpisodeResponse> {

        IdListValidator.assertValid(ids)

        return client
            .get("episode/$ids")
            .accept(HttpStatusCode.OK)
            .body()
    }


    /**
     * Fetches the details of an episode with the given ID from the service.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [EpisodeResponse] representing the details of the episode.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getEpisode(id: Int): EpisodeResponse? = client
        .get("episode/$id")
        .accept(HttpStatusCode.OK)
        .body()

}