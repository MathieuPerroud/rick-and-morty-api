package dev.xnative.cleanrmapi.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class DataStoreImpl(private val context: Context) : DataStore {

    override suspend fun storeInto(key: String, data: Int?) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun storeInto(key: String, data: String?) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun storeInto(key: String, data: Boolean?) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun storeInto(key: String, data: Double?) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun storeInto(key: String, data: Float?) {
        val preferencesKey = floatPreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun storeInto(key: String, data: Long?) {
        val preferencesKey = longPreferencesKey(key)
        context.dataStore.edit { prefs ->
            if (data == null) prefs.remove(preferencesKey)
            else prefs[preferencesKey] = data
        }
    }

    override suspend fun retrieveFromInt(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }

    override suspend fun retrieveFromString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }

    override suspend fun retrieveFromBoolean(key: String): Boolean? {
        val preferencesKey = booleanPreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }

    override suspend fun retrieveFromDouble(key: String): Double? {
        val preferencesKey = doublePreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }

    override suspend fun retrieveFromFloat(key: String): Float? {
        val preferencesKey = floatPreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }

    override suspend fun retrieveFromLong(key: String): Long? {
        val preferencesKey = longPreferencesKey(key)
        return context.dataStore.data.first()[preferencesKey]
    }
}
