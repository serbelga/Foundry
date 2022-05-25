package dev.sergiobelda.foundry.data.api

import okhttp3.Interceptor
import okhttp3.Response

class GoogleApiKeyInterceptor(
    private val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(PARAM_KEY, privateKey)
            .build()

        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val PARAM_KEY = "key"
    }
}
