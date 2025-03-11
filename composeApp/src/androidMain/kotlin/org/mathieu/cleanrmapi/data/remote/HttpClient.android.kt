package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

actual val engine: HttpClientEngine
    get() = CIO.create()