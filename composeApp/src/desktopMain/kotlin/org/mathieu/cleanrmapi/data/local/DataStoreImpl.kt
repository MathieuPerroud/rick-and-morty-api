package org.mathieu.cleanrmapi.data.local

class DataStoreImpl: DataStore {

    private val map: MutableMap<String, Any?> = mutableMapOf()

    override suspend fun <T: Any> storeInto(key: String, data: T?) {
        map[key] = data
    }

    override suspend fun <T: Any> retrieveFrom(key: String): T? {
        return map[key] as T?
    }

}