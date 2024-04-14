package org.mathieu.cleanrmapi.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents detailed information about an episode, typically received from an API response.
 *
 * @property id The unique identifier of the episode.
 * @property name The name of the episode.
 * @property air_date The air date of the episode.
 * @property episode The code of the episode, representing its sequence in the series.
 * @property characters A list of URLs, each pointing to a character that appears in this episode.
 * @property url The URL to this episode's detail page on the API.
 * @property created The timestamp indicating when the episode data was created or first added to the database.
 */
@Serializable
internal data class EpisodeResponse (
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
//{
//    "id":51,
//    "name":"Rickmurai Jack",
//    "air_date":"September 5, 2021",
//    "episode":"S05E10",
//    "characters":["https://rickandmortyapi.com/api/character/1","https://rickandmortyapi.com/api/character/2","https://rickandmortyapi.com/api/character/5","https://rickandmortyapi.com/api/character/4","https://rickandmortyapi.com/api/character/3","https://rickandmortyapi.com/api/character/47","https://rickandmortyapi.com/api/character/787","https://rickandmortyapi.com/api/character/118","https://rickandmortyapi.com/api/character/801","https://rickandmortyapi.com/api/character/802","https://rickandmortyapi.com/api/character/803","https://rickandmortyapi.com/api/character/804","https://rickandmortyapi.com/api/character/805","https://rickandmortyapi.com/api/character/806","https://rickandmortyapi.com/api/character/94","https://rickandmortyapi.com/api/character/807","https://rickandmortyapi.com/api/character/808","https://rickandmortyapi.com/api/character/809","https://rickandmortyapi.com/api/character/810","https://rickandmortyapi.com/api/character/244","https://rickandmortyapi.com/api/character/285","https://rickandmortyapi.com/api/character/274","https://rickandmortyapi.com/api/character/215","https://rickandmortyapi.com/api/character/294","https://rickandmortyapi.com/api/character/389","https://rickandmortyapi.com/api/character/380","https://rickandmortyapi.com/api/character/38","https://rickandmortyapi.com/api/character/811","https://rickandmortyapi.com/api/character/812","https://rickandmortyapi.com/api/character/392","https://rickandmortyapi.com/api/character/813","https://rickandmortyapi.com/api/character/814","https://rickandmortyapi.com/api/character/815","https://rickandmortyapi.com/api/character/816","https://rickandmortyapi.com/api/character/817","https://rickandmortyapi.com/api/character/818","https://rickandmortyapi.com/api/character/819","https://rickandmortyapi.com/api/character/820","https://rickandmortyapi.com/api/character/822","https://rickandmortyapi.com/api/character/823","https://rickandmortyapi.com/api/character/824","https://rickandmortyapi.com/api/character/825"],
//    "url":"https://rickandmortyapi.com/api/episode/51",
//    "created":"2021-10-15T17:00:24.105Z"
//}