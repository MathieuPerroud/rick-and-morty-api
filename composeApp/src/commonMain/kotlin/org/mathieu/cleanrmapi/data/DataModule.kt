package org.mathieu.cleanrmapi.data

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.EpisodeApi
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.data.remote.createHttpClient

private const val RM_API_URL = "https://rickandmortyapi.com/api/"

val remoteModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RM_API_URL
        )
    }
    single { CharacterApi(get()) }
    single { EpisodeApi(get()) }
    single { LocationApi(get()) }
}

expect val repositoriesModule: Module