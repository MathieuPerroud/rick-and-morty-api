package dev.xnative.cleanrmapi.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents a paginated response from an API call.
 *
 * @param T The type of items contained in the results list.
 *
 * @property info Metadata([PaginatedInfo]) about the pagination, including count, number of pages, and links to previous/next pages.
 * @property results A list of items of type [T] returned for the current page.
 */
@Serializable
internal data class PaginatedResponse<T>(
    val info: PaginatedInfo,
    val results: List<T>
)

/**
 * Represents paginated information typically received in API responses.
 *
 * @property count The total number of items in the response.
 * @property pages The total number of pages available.
 * @property next A URL pointing to the next page, or null if there is no next page.
 * @property prev A URL pointing to the previous page, or null if there is no previous page.
 */
@Serializable
internal data class PaginatedInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)