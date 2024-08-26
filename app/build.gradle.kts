import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dev.sergiobelda.gradle.spotless")
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
}

val publicApiKey: String = gradleLocalProperties(
    rootDir, providers
).getProperty("google_fonts_api_key") ?: "\"\""

android {
    namespace = "dev.sergiobelda.foundry"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.sergiobelda.foundry"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        lint {
            abortOnError = false
        }
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.activityCompose)

    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.animation.graphics)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.uiTextGoogleFonts)
    implementation(libs.androidx.compose.ui.toolingPreview)

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)

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
