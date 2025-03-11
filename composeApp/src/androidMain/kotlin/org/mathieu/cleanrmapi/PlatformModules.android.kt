package org.mathieu.cleanrmapi

import org.mathieu.cleanrmapi.data.dataStoreModule
import org.mathieu.cleanrmapi.data.databaseBuilderModule
import org.mathieu.cleanrmapi.data.databaseModule

actual fun platformModules() = listOf(
    databaseModule,
    databaseBuilderModule,
    dataStoreModule
)

