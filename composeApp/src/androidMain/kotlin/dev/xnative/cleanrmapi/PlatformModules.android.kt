package dev.xnative.cleanrmapi

import dev.xnative.cleanrmapi.data.dataStoreModule
import dev.xnative.cleanrmapi.data.databaseBuilderModule
import dev.xnative.cleanrmapi.data.databaseModule

actual fun platformModules() = listOf(
    databaseModule,
    databaseBuilderModule,
    dataStoreModule
)

