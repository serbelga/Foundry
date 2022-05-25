package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.BuildConfig
import dev.sergiobelda.foundry.data.api.Constants.GOOGLE_FONTS_BASE_URL
import dev.sergiobelda.foundry.data.api.Constants.TIMEOUT
import dev.sergiobelda.foundry.data.api.GoogleApiKeyInterceptor
import dev.sergiobelda.foundry.data.api.service.GoogleFontsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

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
