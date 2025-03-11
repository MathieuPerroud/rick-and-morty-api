package org.mathieu.cleanrmapi.data.local

import platform.Foundation.NSUserDefaults

class DataStoreImpl : DataStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override suspend fun storeInto(key: String, data: Int?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setInteger(data.toLong(), forKey = key)
    }

    override suspend fun storeInto(key: String, data: String?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setObject(data, forKey = key)
    }

    override suspend fun storeInto(key: String, data: Boolean?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setBool(data, forKey = key)
    }

    override suspend fun storeInto(key: String, data: Double?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setDouble(data, forKey = key)
    }

    override suspend fun storeInto(key: String, data: Float?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setDouble(data.toDouble(), forKey = key)
    }

    override suspend fun storeInto(key: String, data: Long?) {
        if (data == null) userDefaults.removeObjectForKey(key)
        else userDefaults.setInteger(data, forKey = key)
    }

    override suspend fun retrieveFromInt(key: String): Int? {
        return userDefaults.integerForKey(key).toInt()
    }

    override suspend fun retrieveFromString(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    override suspend fun retrieveFromBoolean(key: String): Boolean? {
        return userDefaults.boolForKey(key)
    }

    override suspend fun retrieveFromDouble(key: String): Double? {
        return userDefaults.doubleForKey(key)
    }

    override suspend fun retrieveFromFloat(key: String): Float? {
        return userDefaults.doubleForKey(key).toFloat()
    }

    override suspend fun retrieveFromLong(key: String): Long? {
        return userDefaults.integerForKey(key)
    }
}
