package dev.xnative.cleanrmapi.characters.api

import dev.xnative.cleanrmapi.characters.navigation.CharactersGraph
import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.characters.data.charactersDataModule
import dev.xnative.cleanrmapi.characters.presentation.navigation.CharactersGraphImpl
import dev.xnative.cleanrmapi.characters.presentation.navigation.CharactersNavigationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val charactersModules: List<Module>
    get() = listOf(
        navigationModule,
        charactersDataModule
    )

private val navigationModule = module {
    single<CharactersNavigation> { CharactersNavigationImpl(get()) }
    single<CharactersGraph> { CharactersGraphImpl() }
}