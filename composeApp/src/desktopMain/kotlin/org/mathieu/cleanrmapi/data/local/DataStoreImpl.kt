package org.mathieu.cleanrmapi.data.local

import java.util.prefs.Preferences

class DataStoreImpl : DataStore {
    private val prefs: Preferences = Preferences.userRoot().node("org.mathieu.cleanrmapi.data.local.preferences")

    override suspend fun storeInto(key: String, data: Int?) {
        if (data == null) prefs.remove(key)
        else prefs.putInt(key, data)
    }

    override suspend fun storeInto(key: String, data: String?) {
        if (data == null) prefs.remove(key)
        else prefs.put(key, data)
    }

    override suspend fun storeInto(key: String, data: Boolean?) {
        if (data == null) prefs.remove(key)
        else prefs.putBoolean(key, data)
    }

    override suspend fun storeInto(key: String, data: Double?) {
        if (data == null) prefs.remove(key)
        else prefs.putDouble(key, data)
    }

    override suspend fun storeInto(key: String, data: Float?) {
        if (data == null) prefs.remove(key)
        else prefs.putFloat(key, data)
    }

    override suspend fun storeInto(key: String, data: Long?) {
        if (data == null) prefs.remove(key)
        else prefs.putLong(key, data)
    }

    override suspend fun retrieveFromInt(key: String): Int? {
        return if (prefs.get(key, null) != null) prefs.getInt(key, 0) else null
    }

    override suspend fun retrieveFromString(key: String): String? {
        return prefs.get(key, null)
    }

    override suspend fun retrieveFromBoolean(key: String): Boolean? {
        return if (prefs.get(key, null) != null) prefs.getBoolean(key, false) else null
    }

    override suspend fun retrieveFromDouble(key: String): Double? {
        return if (prefs.get(key, null) != null) prefs.getDouble(key, 0.0) else null
    }

    override suspend fun retrieveFromFloat(key: String): Float? {
        return if (prefs.get(key, null) != null) prefs.getFloat(key, 0f) else null
    }

    override suspend fun retrieveFromLong(key: String): Long? {
        return if (prefs.get(key, null) != null) prefs.getLong(key, 0L) else null
    }
}
