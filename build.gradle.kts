plugins {
    id("com.android.application") version "7.4.0-alpha08" apply false
    id("com.android.library") version "7.4.0-alpha08" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

subprojects {
    apply {
        from("${rootDir}/ktlint.gradle.kts")
    }
}
