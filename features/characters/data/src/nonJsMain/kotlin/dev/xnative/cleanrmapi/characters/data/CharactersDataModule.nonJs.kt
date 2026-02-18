package dev.xnative.cleanrmapi.characters.data

import dev.xnative.cleanrmapi.characters.data.repositories.CharacterRepositoryImpl
import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual val charactersDataModule: Module
    get() = module {
        single<CharacterRepository> {
            CharacterRepositoryImpl(get(), get(), get(), get())
        }
    }