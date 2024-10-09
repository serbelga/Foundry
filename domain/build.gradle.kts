plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.kotlin.coroutinesCore)
}
