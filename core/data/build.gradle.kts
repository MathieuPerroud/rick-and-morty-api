import dev.xnative.kmpLibrary

plugins {
    id("dev.xnative.kmp-library")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kmpLibrary(
    common = {
        implementation(project.dependencies.platform(libs.koin.bom))

        implementation(project(":core:domain"))
        implementation(project(":core:common"))
        implementation(libs.bundles.koin.common)
        implementation(libs.bundles.kotlinx.common)
        implementation(libs.bundles.ktor.common)
    },
    nonJs = {
        implementation(libs.room.runtime)
        implementation(libs.sqlite.bundled)
    },
    mobile = {
        implementation(libs.bundles.datastore.common)
    },
    android = {
        implementation(libs.bundles.ktor.android)
        implementation(libs.bundles.koin.android)
    },
    ios = {
        implementation(libs.bundles.ktor.ios)
    },
    desktop = {
        implementation(libs.kotlinx.coroutines.swing)
        implementation(libs.bundles.ktor.desktop)
    },
    wasmJs = {
        implementation(libs.bundles.ktor.wasmjs)
    }
)

dependencies {
    listOf(
        "kspAndroid",
        "kspDesktop",
        "kspIosSimulatorArm64",
        "kspIosArm64",
        "kspCommonMainMetadata",
    ).forEach {
        add(it, libs.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.core.data"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
