package dev.xnative.cleanrmapi.data

import dev.xnative.cleanrmapi.data.databaseModule

actual fun platformModules() = listOf(
    databaseModule,
    databaseBuilderModule,
    dataStoreModule
)

