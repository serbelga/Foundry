/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.api.Constants.GOOGLE_FONTS_BASE_URL
import dev.sergiobelda.foundry.data.api.Constants.TIMEOUT
import dev.sergiobelda.foundry.data.api.google.interceptor.GoogleApiKeyInterceptor
import dev.sergiobelda.foundry.data.api.google.service.GoogleFontsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

// TODO: Refactor to inject multiple sources
val networkModule =
    module {
        single {
            val googleApiKeyInterceptor =
                GoogleApiKeyInterceptor(privateKey = BuildConfig.GOOGLE_FONTS_API_KEY)

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(googleApiKeyInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(GOOGLE_FONTS_BASE_URL)
                .client(get()).build()
        }

        single<GoogleFontsApiService> {
            get<Retrofit>().create(GoogleFontsApiService::class.java)
        }
    }
