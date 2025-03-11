package org.mathieu.cleanrmapi

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.mathieu.cleanrmapi.data.dataModules
import org.mathieu.cleanrmapi.data.remoteModule
import org.mathieu.cleanrmapi.data.repositoriesModule

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(dataModules)
        modules(remoteModule, repositoriesModule)
    }

