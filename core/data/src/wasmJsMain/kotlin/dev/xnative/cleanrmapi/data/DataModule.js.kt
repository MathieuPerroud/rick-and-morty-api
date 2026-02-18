package dev.xnative.cleanrmapi.data

import dev.xnative.cleanrmapi.data.local.DataStore
import dev.xnative.cleanrmapi.data.local.DataStoreImpl
import org.koin.dsl.module


val dataStoreModule = module {

    single<DataStore> { DataStoreImpl() }

}