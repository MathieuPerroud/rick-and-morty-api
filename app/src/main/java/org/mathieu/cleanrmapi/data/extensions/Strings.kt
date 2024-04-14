package org.mathieu.cleanrmapi.data.extensions

fun List<String>.extractIdsFromUrls(): String {
    return this.joinToString(",") { url ->
        url.substringAfterLast("/")
    }
}
