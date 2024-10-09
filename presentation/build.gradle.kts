plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "dev.sergiobelda.foundry.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

dependencies {

    implementation(projects.domain)

    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.animation.graphics)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.uiTextGoogleFonts)
    implementation(libs.androidx.compose.ui.toolingPreview)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlin.collections.immutable)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)
}