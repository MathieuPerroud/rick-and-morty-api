package dev.xnative.cleanrmapi.data

import org.koin.core.module.Module
import org.koin.dsl.module
import dev.xnative.cleanrmapi.data.local.CharacterDAO
import dev.xnative.cleanrmapi.data.local.EpisodeDAO
import dev.xnative.cleanrmapi.data.local.RMDatabase
import dev.xnative.cleanrmapi.data.local.getRoomDatabase
import dev.xnative.cleanrmapi.data.repositories.CharacterRepositoryImpl
import dev.xnative.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import dev.xnative.cleanrmapi.domain.character.CharacterRepository
import dev.xnative.cleanrmapi.domain.episode.EpisodeRepository

actual val repositoriesModule = module {

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }

}
/**
 * This module sets up the implementations of RoomDatabase.Builder<RMDatabase>
 */
expect val databaseBuilderModule: Module

expect val dataStoreModule: Module

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