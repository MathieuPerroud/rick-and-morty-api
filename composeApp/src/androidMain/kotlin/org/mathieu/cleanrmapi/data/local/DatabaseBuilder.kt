package org.mathieu.cleanrmapi.data.local

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(application: Application): RoomDatabase.Builder<RMDatabase> {
    val dbFile = application.getDatabasePath("rick_and_morty_database.db")
    return Room.databaseBuilder<RMDatabase>(
        context = application,
        name = dbFile.absolutePath
    )
}
