@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

val publicApiKey: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
    rootDir
).getProperty("google_fonts_api_key") ?: "\"\""

android {
    namespace = "dev.sergiobelda.foundry"
    compileSdk = 32

    defaultConfig {
        applicationId = "dev.sergiobelda.foundry"
        minSdk = 24
        targetSdk = 32
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")

    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.uiTextGoogleFonts)
    implementation(libs.androidx.compose.ui.toolingPreview)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-beta02")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-beta02")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0-beta02")

    implementation(libs.squareup.moshi.moshi)
    kapt(libs.squareup.moshi.moshiKotlinCodegen)
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
