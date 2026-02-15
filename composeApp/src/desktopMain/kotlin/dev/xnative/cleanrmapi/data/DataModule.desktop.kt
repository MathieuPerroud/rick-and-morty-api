package dev.xnative.cleanrmapi.data

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import dev.xnative.cleanrmapi.data.local.DataStore
import dev.xnative.cleanrmapi.data.local.DataStoreImpl
import dev.xnative.cleanrmapi.data.local.RMDatabase
import java.io.File

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
    val dbFile = File(System.getProperty("java.io.tmpdir"), "rick_and_morty_database.db")
    return Room.databaseBuilder<RMDatabase>(
        name = dbFile.absolutePath,
    )
}