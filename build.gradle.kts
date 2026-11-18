// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // AGP 8.9.1 is the minimal version satisfying the AAR metadata floor of the navigation3
    // stack (androidx.navigation3 / lifecycle / navigationevent require AGP 8.9.1+): the frozen
    // AGP line of this branch (w3c-report.md); AGP 9 stays excluded with Kotlin 2.3.10.
    id("com.android.application") version "8.9.1" apply false
    // Kotlin 2.0.21 is the kotlin-stdlib version androidx.navigation3 and androidx.compose declare
    // on their Android variants (metadata 2.0.0): the frozen Kotlin line of this branch
    // (w3c-report.md); do not bump.
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    // Compose compiler ships with Kotlin 2.x as a Gradle plugin (required by navigation3).
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    // KSP: no verbatim build for the frozen Kotlin 2.0.21 line exists in this repository;
    // 2.0.21-1.0.25 is the minimal KSP release on the Kotlin 2.0.21 line (documented
    // deviation, see w7-report.md).
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}
