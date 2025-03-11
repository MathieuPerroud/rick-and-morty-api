package org.mathieu.cleanrmapi.data.local
import kotlinx.browser.localStorage

class DataStoreImpl : DataStore {

    override suspend fun storeInto(key: String, data: Int?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data.toString())
    }

    override suspend fun storeInto(key: String, data: String?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data)
    }

    override suspend fun storeInto(key: String, data: Boolean?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data.toString())
    }

    override suspend fun storeInto(key: String, data: Double?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data.toString())
    }

    override suspend fun storeInto(key: String, data: Float?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data.toString())
    }

    override suspend fun storeInto(key: String, data: Long?) {
        if (data == null) localStorage.removeItem(key)
        else localStorage.setItem(key, data.toString())
    }

    override suspend fun retrieveFromInt(key: String): Int? {
        return localStorage.getItem(key)?.toIntOrNull()
    }

    override suspend fun retrieveFromString(key: String): String? {
        return localStorage.getItem(key)
    }

    override suspend fun retrieveFromBoolean(key: String): Boolean? {
        return localStorage.getItem(key)?.toBooleanStrictOrNull()
    }

    override suspend fun retrieveFromDouble(key: String): Double? {
        return localStorage.getItem(key)?.toDoubleOrNull()
    }

    override suspend fun retrieveFromFloat(key: String): Float? {
        return localStorage.getItem(key)?.toFloatOrNull()
    }

    override suspend fun retrieveFromLong(key: String): Long? {
        return localStorage.getItem(key)?.toLongOrNull()
    }
}
