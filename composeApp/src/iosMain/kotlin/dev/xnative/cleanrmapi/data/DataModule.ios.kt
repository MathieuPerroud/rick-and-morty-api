package dev.xnative.cleanrmapi.data

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import dev.xnative.cleanrmapi.data.local.DataStore
import dev.xnative.cleanrmapi.data.local.DataStoreImpl
import dev.xnative.cleanrmapi.data.local.RMDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val databaseBuilderModule: Module
    get() = module {

        single<RoomDatabase.Builder<RMDatabase>> {
            getDatabaseBuilder()
        }

    }

actual val dataStoreModule = module {

    single<DataStore> { DataStoreImpl() }

}

private fun getDatabaseBuilder(): RoomDatabase.Builder<RMDatabase> {
    val dbFilePath = documentDirectory() + "/rick_and_morty_database.db"
    return Room.databaseBuilder<RMDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}