package org.mathieu.cleanrmapi.data.remote

import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit

class HttpClient : KoinComponent {

    val client: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)

        client = builder.build()
    }
}
