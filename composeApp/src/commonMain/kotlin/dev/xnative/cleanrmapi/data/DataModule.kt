package dev.xnative.cleanrmapi.data

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import dev.xnative.cleanrmapi.data.remote.CharacterApi
import dev.xnative.cleanrmapi.data.remote.EpisodeApi
import dev.xnative.cleanrmapi.data.remote.createHttpClient

private const val RM_API_URL = "https://rickandmortyapi.com/api/"

val remoteModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RM_API_URL
        )
    }
    single { CharacterApi(get()) }
    single { EpisodeApi(get()) }
}

expect val repositoriesModule: Module