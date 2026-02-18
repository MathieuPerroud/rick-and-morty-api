import dev.xnative.kmpLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("dev.xnative.kmp-library")
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kmpLibrary(
    common = {
        implementation(project.dependencies.platform(libs.koin.bom))

        implementation(project(":features:episodes:navigation"))
        implementation(project(":core:navigation"))
        implementation(project(":features:episodes:data"))
        implementation(project(":features:episodes:presentation"))
        implementation(libs.bundles.compose.common)
        implementation(libs.bundles.koin.common)
        implementation(libs.bundles.kotlinx.common)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.episodes.api"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }

    }
}
