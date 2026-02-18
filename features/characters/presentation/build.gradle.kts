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

        implementation(project(":core:domain"))
        implementation(project(":core:navigation"))
        implementation(project(":core:presentation"))
        implementation(project(":features:characters:domain"))
        implementation(project(":features:characters:navigation"))
        implementation(project(":features:episodes:navigation"))

        implementation(libs.navigation3.ui)

        implementation(libs.bundles.koin.common)
        implementation(libs.bundles.kotlinx.common)

        implementation(libs.bundles.coil.common)

        implementation(libs.bundles.compose.common)
        implementation(libs.compose.material.icons.extended)

        implementation(libs.lifecycle.viewmodel.compose)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.characters.presentation"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        androidResources {
            enable = true
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
