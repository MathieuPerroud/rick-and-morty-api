package dev.xnative.cleanrmapi.data

import dev.xnative.cleanrmapi.data.local.CharacterDAO
import dev.xnative.cleanrmapi.data.local.EpisodeDAO
import dev.xnative.cleanrmapi.data.local.RMDatabase
import dev.xnative.cleanrmapi.data.local.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

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