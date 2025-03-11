package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val engine: HttpClientEngine
    get() = Darwin.create()