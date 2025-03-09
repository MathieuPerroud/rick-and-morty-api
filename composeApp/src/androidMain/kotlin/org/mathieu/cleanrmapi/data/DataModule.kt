package org.mathieu.cleanrmapi.data

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.EpisodeDAO
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.getDatabaseBuilder
import org.mathieu.cleanrmapi.data.local.getRoomDatabase
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

    single<RMDatabase> {
        getRoomDatabase(
            getDatabaseBuilder(get())
        )
    }


    single<CharacterDAO> {
        val db: RMDatabase = get()
        db.characterDAO()
    }

    single { CharacterApi(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }


    single { EpisodeApi(get()) }

    single<EpisodeDAO> {
        val db: RMDatabase = get()
        db.episodeDAO()
    }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }

}