/*
 * Copyright 2022 Sergio Belda Galbis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.foundry.domain.result

sealed class Result<out A> {
    data class Success<A>(val value: A) : Result<A>()

    data class Error(val exception: Throwable? = null) : Result<Nothing>()

    fun <B> map(m: ((A) -> B)): Result<B> =
        when (this) {
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
