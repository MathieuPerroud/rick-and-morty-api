package org.mathieu.cleanrmapi

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.mathieu.cleanrmapi.data.remoteModule
import org.mathieu.cleanrmapi.data.repositoriesModule

expect fun platformModules(): List<Module>

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(platformModules())
        modules(remoteModule, repositoriesModule)
    }

