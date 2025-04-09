import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    kotlin("plugin.serialization")
}
kotlin {
    
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    jvm("desktop")

    sourceSets {

        val desktopMain by getting

        all {
            languageSettings {
                compilerOptions{
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }

        // Dependencies for every platforms
        commonMain.dependencies {
            //Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.navigation)
            implementation(compose.materialIconsExtended)
            implementation(libs.lifecycle.viewmodel.compose)

            coil()

            kotlinx()

            koin(platform = Platform.Common)

            ktor(platform = Platform.Common)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }

        // Specific platforms
        androidMain.dependencies {
            ktor(platform = Platform.Android)
            koin(platform = Platform.Android)
            datastore()
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            koin(platform = Platform.Desktop)
            ktor(platform = Platform.Desktop)

        }

    }

}


compose.desktop {
    application {
        mainClass = "org.mathieu.cleanrmapi.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.mathieu.cleanrmapi"
            packageVersion = "1.0.0"
        }
    }
}

android {
    namespace = "org.mathieu.cleanrmapi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.mathieu.cleanrmapi"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.firebase.crashlytics.buildtools)
    // KSP support for Room Compiler.
    listOf(
        "kspAndroid",
        "kspDesktop",
        "kspCommonMainMetadata",
    ).forEach {
        add(it, libs.room.compiler)
    }

    //Testing
    debugImplementation(compose.uiTooling)

}

room {
    schemaDirectory("$projectDir/schemas")
}

private enum class Platform {
    Common, Android, Desktop
}

private fun KotlinDependencyHandler.ktor(
    platform: Platform
) {
    when (platform) {
        Platform.Android -> {
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.okhttp)
        }
        Platform.Desktop -> {
            implementation(libs.ktor.client.java)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
        }
        Platform.Common -> {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.json)
        }
    }
}

private fun KotlinDependencyHandler.koin(
    platform: Platform
) {
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.compose)

    if (platform == Platform.Android) {
        implementation(libs.koin.android)
    }
    implementation(libs.koin.core)
}

private fun KotlinDependencyHandler.kotlinx() {
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.serialization.json)
}
private fun KotlinDependencyHandler.datastore() {
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)
}

private fun KotlinDependencyHandler.coil() {
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor)
}
