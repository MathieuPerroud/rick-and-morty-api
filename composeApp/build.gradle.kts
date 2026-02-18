import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.application")
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {
    androidTarget {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_2_3)
        }
    }

    listOf(
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

            implementation(project(":core:app"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))


            implementation(libs.bundles.koin.common)
            implementation(libs.bundles.kotlinx.common)

            implementation(libs.bundles.coil.common)

            implementation(libs.bundles.compose.common)
            implementation(libs.compose.material.icons.extended)

            implementation(libs.lifecycle.viewmodel.compose)

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }


        androidMain.dependencies {
            implementation(libs.compose.ui.preview.android)
            implementation(libs.androidx.activity.compose)
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
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
