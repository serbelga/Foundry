package dev.sergiobelda.foundry.domain.result

sealed class Result<out A> {
    data class Success<A>(val value: A) : Result<A>()
    data class Error(val exception: Throwable? = null) : Result<Nothing>()

    fun <B> map(m: ((A) -> B)): Result<B> = when (this) {
        is Success -> Success(m(value))
        is Error -> Error(exception)
    }
}

/**
 * Call the specific action in [callback] if the result is [Result.Success] and not null.
 */
inline fun <reified A> Result<A>.doIfSuccess(callback: (value: A) -> Unit): Result<A> {
    (this as? Result.Success)?.value?.let { callback(it) }
    return this
}

/**
 * Call the specific action in [callback] if the result is [Result.Error].
 */
inline fun <reified A> Result<A>.doIfError(callback: (error: Result.Error) -> Unit): Result<A> {
    (this as? Result.Error)?.let { callback(it) }
    return this
}
