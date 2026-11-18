// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // AGP 9.5.1: latest stable Android Gradle Plugin (developer.android.com/build/releases/
    // gradle-plugin; 9.6.0-alpha04 is a preview and is not taken).
    id("com.android.application") version "9.5.1" apply false
    // AGP 9 provides built-in Kotlin: `org.jetbrains.kotlin.android` must NOT be applied any
    // more (developer.android.com/build/migrate-to-built-in-kotlin). The Kotlin Gradle plugin
    // version used by built-in Kotlin is taken from the build classpath, pinned here at 2.4.21:
    // latest stable Kotlin (kotlinlang.org/docs/releases.html, 2026-10-08; Maven Central
    // kotlin-gradle-plugin metadata shows 2.5.0-RC as latest, a preview).
    // Compose compiler ships with Kotlin 2.x as a Gradle plugin: versioned 1:1 with Kotlin.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
    // KSP is version-independent of Kotlin since 2.3.0: 2.3.14 is the latest stable release
    // (Maven Central symbol-processing-api metadata, github.com/google/ksp/releases).
    id("com.google.devtools.ksp") version "2.3.14" apply false
    kotlin("jvm") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
}
