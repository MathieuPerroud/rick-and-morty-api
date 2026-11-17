// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // AGP 8.9.1 is the minimal version satisfying the AAR metadata floor of the navigation3
    // stack (androidx.navigation3 / lifecycle / navigationevent require AGP 8.9.1+); AGP 9 is
    // excluded because it forces kotlin-gradle-plugin 2.2.10 onto the buildscript classpath,
    // which the Realm compiler plugin (3.0.0, Kotlin 2.0 line) cannot run under.
    id("com.android.application") version "8.9.1" apply false
    // Kotlin 2.0.21 is the kotlin-stdlib version androidx.navigation3 and androidx.compose declare
    // on their Android variants (metadata 2.0.0) and the newest line Realm 3.0.0 is built against.
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    // Compose compiler ships with Kotlin 2.x as a Gradle plugin (required by navigation3).
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id("io.realm.kotlin") version "3.0.0" apply false
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}
