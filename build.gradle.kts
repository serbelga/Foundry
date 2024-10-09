@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinJvm) apply false
}
