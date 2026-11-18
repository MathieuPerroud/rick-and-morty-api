package org.mathieu.cleanrmapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.mathieu.cleanrmapi.data.local.objects.CharacterEntity

/**
 * Room database of the application's local character store.
 *
 * Successor of the former Realm `RMDatabase` / `RealmDatabase` pair: a single entity
 * table, schema version 1, destructive fallback instead of Realm's
 * `deleteRealmIfMigrationNeeded()` — the same "no production migration yet" posture the
 * Realm configuration documented. Provisioned by the Koin `dataModule` through
 * `Room.databaseBuilder`.
 */
@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    /** Access point to the character table. */
    abstract fun characterDao(): CharacterDao

    companion object {
        /** Persisted table name (carried over verbatim from the proven Room wiring). */
        const val CHARACTER_TABLE = "character_table"
    }

}
