package org.mathieu.cleanrmapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject

@Database(
    entities = [
        CharacterObject::class,
        EpisodeObject::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RMDatabase: RoomDatabase() {

    abstract fun characterDAO(): CharacterDAO
    abstract fun episodeDAO(): EpisodeDAO

    companion object {
        const val CHARACTER_TABLE = "character_table"
        const val EPISODE_TABLE = "episode_table"
    }

}