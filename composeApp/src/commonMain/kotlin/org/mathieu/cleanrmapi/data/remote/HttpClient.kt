package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*

expect val engine: HttpClientEngine

fun createHttpClient(
    baseUrl: String,
    engine: HttpClientEngine = org.mathieu.cleanrmapi.data.remote.engine
): HttpClient = HttpClient(engine) {
    defaultRequest { url(baseUrl) }


    install(ContentNegotiation) {
        json()
    }

    install(HttpTimeout) {
        connectTimeoutMillis = 15000
        socketTimeoutMillis = 15000
        requestTimeoutMillis = 15000
    }

    install(HttpCallValidator) {
        validateResponse {
            when (it.status.value) {
                HttpStatusCode.Forbidden.value -> throw HttpException.ForbiddenAccess
                in 500..599 -> throw HttpException.InternalServerError
            }
        }

    }
}

inline fun <reified T> HttpRequestBuilder.setBodyJson(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

fun HttpResponse.accept(vararg codes: HttpStatusCode) = when (status) {
    in codes -> this
    else -> throw HttpException.NotAccepted
}

sealed class HttpException(override val message: String): Exception() {
    object NotAccepted: HttpException(message = "Http code not accepted.")
    object ForbiddenAccess: HttpException(message = "Forbidden access.")
    object InternalServerError: HttpException(message = "Internal server error.")
}