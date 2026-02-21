import dev.xnative.kmpLibrary

plugins {
    id("dev.xnative.kmp-library")
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kmpLibrary(
    common = {
        implementation(project.dependencies.platform(libs.koin.bom))

        implementation(project(":core:data"))
        implementation(project(":core:navigation"))
        implementation(project(":core:presentation"))
        implementation(project(":features:episodes:api"))
        implementation(project(":features:characters:api"))
        implementation(project(":features:episodes:navigation"))
        implementation(project(":features:characters:navigation"))

        implementation(libs.navigation3.ui)
        implementation(libs.lifecycle.viewmodel.navigation3)

        implementation(libs.bundles.koin.common)
        implementation(libs.bundles.compose.common)
        implementation(libs.compose.material.icons.extended)

        implementation(libs.lifecycle.viewmodel.compose)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.core.app"

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
