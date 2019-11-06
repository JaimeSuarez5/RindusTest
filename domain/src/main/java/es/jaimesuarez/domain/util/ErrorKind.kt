package es.jaimesuarez.domain.util

sealed class ErrorKind(val exception: Exception?) {
    class ApiError(exception: Exception? = null) : ErrorKind(exception)
    class CacheError(exception: Exception? = null) : ErrorKind(exception)
    class UnknownError(exception: Exception? = null) : ErrorKind(exception)
}
