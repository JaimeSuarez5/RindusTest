package es.jaimesuarez.data.datasource.local

import es.jaimesuarez.domain.util.ErrorKind
import es.jaimesuarez.domain.util.Result

class LocalCacheDatasource<T> {

    private val cache: MutableMap<Int, Result<T>> = mutableMapOf()

    fun isCached(key: Int): Boolean = cache.containsKey(key)

    fun getCached(key: Int): Result<T> = cache[key] ?: Result.Failure(ErrorKind.CacheError())

    fun save(id: Int, item: Result<T>) {
        cache[id] = item
    }
}
