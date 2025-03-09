package org.mathieu.cleanrmapi.data.local

//import android.content.Context TODO: Implement real datastore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.byteArrayPreferencesKey
//import androidx.datastore.preferences.core.doublePreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.floatPreferencesKey
//import androidx.datastore.preferences.core.intPreferencesKey
//import androidx.datastore.preferences.core.longPreferencesKey
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map


class DataStoreImpl(
//    private val context: Context,
//    name: String
): DataStore {

    private val map: MutableMap<String, Any?> = mutableMapOf()

    override suspend fun <T: Any> storeInto(key: String, data: T?) {
        map[key] = data
    }

    override suspend fun <T: Any> retrieveFrom(key: String): T? {
        return map[key] as T?
    }

//    private val Context.dataStore by preferencesDataStore(
//        name = name
//    )
//
//    override suspend fun <T: Any> storeInto(key: String, data: T) {
//        val prefKey = getAnyPreferenceKey<T>(key)
//        context.dataStore.edit { prefs -> prefs[prefKey] = data }
//
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    private inline fun <reified T: Any> getAnyPreferenceKey(name: String): Preferences.Key<T> =
//        when (T::class) {
//            Int::class -> intPreferencesKey(name)
//            Long::class -> longPreferencesKey(name)
//            Float::class -> floatPreferencesKey(name)
//            Double::class -> doublePreferencesKey(name)
//            Boolean::class -> booleanPreferencesKey(name)
//            ByteArray::class -> byteArrayPreferencesKey(name)
//            else -> stringPreferencesKey(name)
//        } as Preferences.Key<T>
//
//
//    override suspend fun <T: Any> retrieveFrom(key: String): T {
//        val prefKey = getAnyPreferenceKey(key)
//        return context.dataStore.data.map { prefs -> prefs[prefKey] }.first()
//    }
}