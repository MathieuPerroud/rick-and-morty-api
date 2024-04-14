package org.mathieu.cleanrmapi.data

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterLocal
import org.mathieu.cleanrmapi.data.local.EpisodeLocal
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.RealmDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.EpisodeApi
import org.mathieu.cleanrmapi.data.remote.createHttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

val dataModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    single<RealmDatabase> { RMDatabase() }


    single { CharacterLocal(get()) }

    single { CharacterApi(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }


    single { EpisodeApi(get()) }

    single { EpisodeLocal(get()) }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }


}