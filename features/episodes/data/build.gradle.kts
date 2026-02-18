import dev.xnative.kmpLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("dev.xnative.kmp-library")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kmpLibrary(
    common = {
        implementation(project.dependencies.platform(libs.koin.bom))

        implementation(project(":core:domain"))
        implementation(project(":core:common"))
        implementation(project(":core:data"))
        implementation(project(":features:episodes:domain"))
        implementation(libs.bundles.koin.common)
        implementation(libs.bundles.kotlinx.common)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.episodes.data"

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
