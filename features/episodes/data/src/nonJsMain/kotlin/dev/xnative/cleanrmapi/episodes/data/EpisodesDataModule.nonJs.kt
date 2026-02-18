package dev.xnative.cleanrmapi.episodes.data

import dev.xnative.cleanrmapi.domain.episode.EpisodeRepository
import dev.xnative.cleanrmapi.episodes.data.repositories.EpisodeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val episodesDataModule: Module
    get() = module {
        single<EpisodeRepository> {
            EpisodeRepositoryImpl(get())
        }
    }