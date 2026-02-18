package dev.xnative.cleanrmapi.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.java.Java

actual val engine: HttpClientEngine
    get() = Java.create()