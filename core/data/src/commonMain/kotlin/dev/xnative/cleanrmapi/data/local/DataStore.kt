package dev.xnative.cleanrmapi.data.local

interface DataStore {
    suspend fun storeInto(key: String, data: Int?)
    suspend fun storeInto(key: String, data: String?)
    suspend fun storeInto(key: String, data: Boolean?)
    suspend fun storeInto(key: String, data: Double?)
    suspend fun storeInto(key: String, data: Float?)
    suspend fun storeInto(key: String, data: Long?)

    suspend fun retrieveFromInt(key: String): Int?
    suspend fun retrieveFromString(key: String): String?
    suspend fun retrieveFromBoolean(key: String): Boolean?
    suspend fun retrieveFromDouble(key: String): Double?
    suspend fun retrieveFromFloat(key: String): Float?
    suspend fun retrieveFromLong(key: String): Long?
}
