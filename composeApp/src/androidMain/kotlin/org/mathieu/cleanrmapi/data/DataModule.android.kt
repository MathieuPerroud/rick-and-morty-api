package org.mathieu.cleanrmapi.data

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.DataStore
import org.mathieu.cleanrmapi.data.local.DataStoreImpl
import org.mathieu.cleanrmapi.data.local.RMDatabase

actual val databaseBuilderModule: Module
    get() = module {

        single<RoomDatabase.Builder<RMDatabase>> {
            getDatabaseBuilder(get())
        }

    }

actual val dataStoreModule = module {

    single<DataStore> { DataStoreImpl(get()) }

}

private fun getDatabaseBuilder(application: Application): RoomDatabase.Builder<RMDatabase> {
    val dbFile = application.getDatabasePath("rick_and_morty_database.db")
    return Room.databaseBuilder<RMDatabase>(
        context = application,
        name = dbFile.absolutePath
    )
}
