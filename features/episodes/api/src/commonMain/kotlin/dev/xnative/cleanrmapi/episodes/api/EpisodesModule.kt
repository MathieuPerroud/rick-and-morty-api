package dev.xnative.cleanrmapi.episodes.api

import dev.xnative.cleanrmapi.episodes.data.episodesDataModule
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesGraph
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import dev.xnative.cleanrmapi.episodes.presentation.navigation.EpisodesGraphImpl
import dev.xnative.cleanrmapi.episodes.presentation.navigation.EpisodesNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val episodesModules: List<Module>
    get() = listOf(
        navigationModule,
        episodesDataModule
    )

private val navigationModule = module {
    single<EpisodesNavigation> { EpisodesNavigationImpl(get()) }
    single<EpisodesGraph> { EpisodesGraphImpl() }
}