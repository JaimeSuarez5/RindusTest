package es.jaimesuarez.data.extensions

import es.jaimesuarez.domain.util.ErrorKind
import es.jaimesuarez.domain.util.Result
import retrofit2.Response

fun <T : Any> Response<T>.perform(): Result<T> {
    return try {
        if (this.isSuccessful && this.body() != null) Result.Success(this.body()!!)
        else Result.Failure(ErrorKind.ApiError())
    } catch (exception: Exception) {
        Result.Failure(ErrorKind.ApiError(exception))
    }
}
