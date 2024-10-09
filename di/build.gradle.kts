import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
}

val publicApiKey: String = gradleLocalProperties(
    rootDir, providers
).getProperty("google_fonts_api_key") ?: "\"\""

android {
    namespace = "dev.sergiobelda.foundry.di"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        compileSdk = libs.versions.androidMinSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "GOOGLE_FONTS_API_KEY", publicApiKey)
        }
        release {
            buildConfigField("String", "GOOGLE_FONTS_API_KEY", publicApiKey)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)

    implementation(libs.squareup.moshi.moshi)
    ksp(libs.squareup.moshi.moshiKotlinCodegen)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.okhttp3.loggingInterceptor)
    implementation(libs.squareup.retrofit2.converterMoshi)
    implementation(libs.squareup.retrofit2.converterScalars)
    implementation(libs.squareup.retrofit2.retrofit)

    implementation(libs.androidx.room.roomKtx)
    implementation(libs.androidx.room.roomPaging)
    implementation(libs.androidx.room.roomRuntime)
    ksp(libs.androidx.room.roomCompiler)
    androidTestImplementation(libs.androidx.room.roomTesting)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)
}