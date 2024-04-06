package org.mathieu.cleanrmapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject

@Database(
    entities = [
        CharacterObject::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RMDatabase: RoomDatabase() {

    abstract fun characterDAO(): CharacterDAO

    companion object {
        const val CHARACTER_TABLE = "character_table"
    }

}