package dev.xnative.cleanrmapi.data

import dev.xnative.cleanrmapi.data.local.DataStore
import dev.xnative.cleanrmapi.data.local.DataStoreImpl
import dev.xnative.cleanrmapi.data.repositories.CharacterRepositoryImpl
import dev.xnative.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import dev.xnative.cleanrmapi.domain.character.CharacterRepository
import dev.xnative.cleanrmapi.domain.episode.EpisodeRepository
import org.koin.dsl.module

actual val repositoriesModule = module {

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }

    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }

}

val dataStoreModule = module {

    single<DataStore> { DataStoreImpl() }

}