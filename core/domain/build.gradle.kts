import dev.xnative.kmpLibrary

plugins {
    id("dev.xnative.kmp-library")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kmpLibrary(
    common = {
        implementation(project.dependencies.platform(libs.koin.bom))
        implementation(libs.bundles.kotlinx.common)
        implementation(libs.bundles.koin.common)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.core.domain"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
