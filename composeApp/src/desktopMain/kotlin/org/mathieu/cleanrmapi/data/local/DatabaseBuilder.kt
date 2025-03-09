package org.mathieu.cleanrmapi.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<RMDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "rick_and_morty_database.db")
    return Room.databaseBuilder<RMDatabase>(
        name = dbFile.absolutePath,
    )
}