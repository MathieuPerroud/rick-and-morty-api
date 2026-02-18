package dev.xnative.cleanrmapi.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual val engine: HttpClientEngine
    get() = Js.create()