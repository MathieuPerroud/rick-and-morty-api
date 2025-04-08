package org.mathieu.cleanrmapi

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.mathieu.cleanrmapi.data.databaseModule
import org.mathieu.cleanrmapi.data.remoteModule
import org.mathieu.cleanrmapi.data.repositoriesModule


fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            remoteModule,
            repositoriesModule,
            databaseModule,
            org.mathieu.cleanrmapi.data.dataStoreModule,
            org.mathieu.cleanrmapi.data.databaseBuilderModule
        )
    }

