package org.mathieu.cleanrmapi.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

@Database(
    entities = [
        CharacterObject::class,
        EpisodeObject::class,
        LocationObject::class
    ],
    version = 1,
    exportSchema = false
)

@ConstructedBy(AppDatabaseConstructor::class)
abstract class RMDatabase: RoomDatabase() {

    abstract fun characterDAO(): CharacterDAO
    abstract fun episodeDAO(): EpisodeDAO
    abstract fun locationDAO(): LocationDAO

    companion object {
        const val CHARACTER_TABLE = "character_table"
        const val EPISODE_TABLE = "episode_table"
        const val LOCATION_TABLE = "location_table"
    }

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<RMDatabase> {
    override fun initialize(): RMDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<RMDatabase>
): RMDatabase = builder
    .addMigrations()
    .fallbackToDestructiveMigrationOnDowngrade(true)
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()