package org.mathieu.cleanrmapi.data

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.EpisodeDAO
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.getRoomDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.EpisodeApi
import org.mathieu.cleanrmapi.data.remote.createHttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository

private const val RM_API_URL = "https://rickandmortyapi.com/api/"

expect val databaseBuilderModule: Module

expect val dataStoreModule: Module

val remoteModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RM_API_URL
        )
    }
    single { CharacterApi(get()) }
    single { EpisodeApi(get()) }
}

val repositoriesModule = module {

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }

}

val databaseModule = module {

    single<RMDatabase> {
        getRoomDatabase(get())
    }

    single<CharacterDAO> {
        val db: RMDatabase = get()
        db.characterDAO()
    }

    single<EpisodeDAO> {
        val db: RMDatabase = get()
        db.episodeDAO()
    }

}