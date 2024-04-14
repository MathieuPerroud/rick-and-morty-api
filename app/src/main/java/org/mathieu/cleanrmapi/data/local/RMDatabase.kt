package org.mathieu.cleanrmapi.data.local

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.TypedRealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject
import kotlin.reflect.KClass

internal class RMDatabase : RealmDatabase(
    "rick and morty",
    setOf(
        CharacterObject::class,
        EpisodeObject::class
    ),
    2
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


    suspend inline fun <reified T : TypedRealmObject> getAll(): Flow<List<T>> = use {
        this.getAll()
    }

    suspend inline fun <reified T : TypedRealmObject> getOneById(id: Int): T? = use {
        this.getOneById(id)
    }

    suspend inline fun <reified T : RealmObject> insert(element: T): T = use {
        this.insert(element)
    }

    suspend inline fun <reified T : RealmObject> insert(elements: List<T>): List<T> = use {
        this.insert(elements)
    }


}

inline fun <reified T : TypedRealmObject> Realm.getAll(): Flow<List<T>>
    = query<T>().find().asFlow().map { it.list }

inline fun <reified T : TypedRealmObject> Realm.getOneById(id: Int): T?
    = query<T>("id == $id").first().find()

suspend inline fun <reified T : RealmObject> Realm.insert(element: T): T =
    this.write {
        copyToRealm(element, UpdatePolicy.ALL)
    }

suspend inline fun <reified T : RealmObject> Realm.insert(elements: List<T>): List<T> =
    elements.onEach {
        insert(it)
    }
