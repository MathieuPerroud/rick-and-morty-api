import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }


    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {

        common {
            group("nonJs") {

                group("mobile") {
                    withAndroidTarget()
                    group("ios") {
                        withIos()
                    }
                }

                withJvm()
            }
        }

    }


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
            implementation(project.dependencies.platform(libs.koin.bom))

            implementation(libs.bundles.koin.common)
            implementation(libs.bundles.kotlinx.common)
            implementation(libs.bundles.ktor.common)
            implementation(libs.bundles.coil.common)

            implementation(libs.bundles.compose.common)
            implementation(libs.compose.material.icons.extended)

            implementation(libs.lifecycle.viewmodel.compose)
            
        }

        // Dependencies for every platforms but wasmJs
        val nonJsMain by getting {
            dependencies {
                //room
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
            }
        }

        // Dependencies for iOS and Android only
        val mobileMain by getting {
            dependencies {
                implementation(libs.bundles.datastore.common)
            }
        }


        // Specific platforms
        androidMain.dependencies {
            implementation(libs.bundles.ktor.android)
            implementation(libs.bundles.koin.android)
        }

        iosMain.dependencies {
            implementation(libs.bundles.ktor.ios)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            
            implementation(libs.bundles.ktor.desktop)
        }

        wasmJsMain.dependencies {
            implementation(libs.bundles.ktor.wasmjs)
        }

    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        if (name.contains("ios")) {
            compilerOptions.freeCompilerArgs.add("-Xskip-metadata-version-check")
        }
    }

}


compose.desktop {
    application {
        mainClass = "dev.xnative.cleanrmapi.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.xnative.cleanrmapi"
            packageVersion = "1.0.0"
        }
    }
}

android {
    namespace = "dev.xnative.cleanrmapi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.xnative.cleanrmapi"
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

    // KSP support for Room Compiler.
    listOf(
        "kspAndroid",
        "kspDesktop",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64",
        "kspCommonMainMetadata",
    ).forEach {
        add(it, libs.room.compiler)
    }

}

room {
    schemaDirectory("$projectDir/schemas")
}
