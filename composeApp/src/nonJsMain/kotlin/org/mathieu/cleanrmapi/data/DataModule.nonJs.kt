package org.mathieu.cleanrmapi.data

import org.koin.core.module.Module
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.EpisodeDAO
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.getRoomDatabase
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.LocationRepositoryImpl
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.episode.EpisodeRepository
import org.mathieu.cleanrmapi.domain.location.LocationRepository

actual val repositoriesModule = module {

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get()) }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }

    single<LocationRepository> { LocationRepositoryImpl(get()) }

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

    single<LocationDAO> {
        val db : RMDatabase = get()
        db.locationDAO()
    }
}