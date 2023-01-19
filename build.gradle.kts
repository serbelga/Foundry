@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply("${rootDir}/spotless.gradle")
    apply("${rootDir}/gradle/ktlint.gradle.kts")
}
