package dev.xnative.cleanrmapi.app.di

import dev.xnative.cleanrmapi.characters.api.charactersModules
import dev.xnative.cleanrmapi.data.platformModules
import dev.xnative.cleanrmapi.data.remoteModule
import dev.xnative.cleanrmapi.episodes.api.episodesModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Bootstraps Koin with platform + feature modules.
 */
fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(platformModules())
        modules(remoteModule)
        modules(charactersModules)
        modules(episodesModules)
    }
