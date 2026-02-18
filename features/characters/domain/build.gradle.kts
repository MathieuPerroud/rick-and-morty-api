import dev.xnative.kmpLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("dev.xnative.kmp-library")
}

kmpLibrary(
    common = {
        implementation(libs.bundles.kotlinx.common)
        implementation(project(":core:domain"))
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.characters.domain"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
