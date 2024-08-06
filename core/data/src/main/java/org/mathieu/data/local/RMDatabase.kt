package org.mathieu.data.local

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import org.mathieu.data.local.objects.CharacterObject
import kotlin.reflect.KClass

internal class RMDatabase : RealmDatabase(
    "rick and morty",
    setOf(
        CharacterObject::class
    ),
    1
)

open class RealmDatabase(name: String, schema: Set<KClass<out TypedRealmObject>>, schemaVersion: Long) {
    private val configuration = RealmConfiguration.Builder(schema)
        .name(name)
        .schemaVersion(schemaVersion)
        //We'd rather prefer performing real schema migration in production environment, see : https://www.mongodb.com/developer/products/realm/realm-sdk-schema-migration-android/
        .deleteRealmIfMigrationNeeded()
        .build()

    private var realm: Realm? = null

    suspend fun <R> use(block: suspend Realm.() -> R): R {
        realm = realm ?: Realm.open(configuration)
        return try {
            block(realm!!)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun <R> write(block: MutableRealm.() -> R): R = use { this.write(block) }

}