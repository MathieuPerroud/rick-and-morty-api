package org.mathieu.cleanrmapi.data.local

interface DataStore {
    suspend fun <T: Any> storeInto(key: String, data: T?)
    suspend fun <T: Any> retrieveFrom(key: String): T?
}