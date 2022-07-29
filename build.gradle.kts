@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version "7.4.0-alpha08" apply false
    id("com.android.library") version "7.4.0-alpha08" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply("${rootDir}/spotless.gradle")
    apply {
        from("${rootDir}/ktlint.gradle.kts")
    }
}
