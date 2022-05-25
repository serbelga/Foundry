package dev.sergiobelda.foundry.data.api

import dev.sergiobelda.foundry.domain.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>
): Result<T> = withContext(dispatcher) {
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(exception = Exception())
        } else {
            Result.Error(exception = Exception())
        }
    } catch (exception: Exception) {
        Result.Error(exception = exception)
    }
}
