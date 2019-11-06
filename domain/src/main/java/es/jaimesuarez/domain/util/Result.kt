package es.jaimesuarez.domain.util

sealed class Result<out T> {

    class Success<out T>(val data: T) : Result<T>()

    class Failure(val error: ErrorKind) : Result<Nothing>()

    fun <R> mapResult(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(error)
        }
    }
}
