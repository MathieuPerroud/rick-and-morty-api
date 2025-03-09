import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

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
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
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
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
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

        //TODO: rename sources with numbers and underscore in order to sort it as they are declared. (e.g. 1_commonMain)

        all {
            languageSettings {
                compilerOptions{
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }

        // Dependencies for every platforms
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //JetBrains
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

            // koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // ktor
            ktor(platform = Platform.Common)


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
                //DataStore
                datastore()
            }
        }

        // Specific platforms
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)

            //Compose
            implementation(platform("androidx.compose:compose-bom:2025.02.00"))
            implementation("androidx.compose.ui:ui")
            implementation("androidx.compose.ui:ui-graphics")
            implementation("androidx.compose.ui:ui-tooling-preview")
            implementation("androidx.compose.material3:material3")
            implementation("androidx.compose.material:material-icons-extended")
            implementation("androidx.navigation:navigation-compose:2.8.8")


            // Coil
            coil()

            // Ktor
            ktor(platform = Platform.Android)
        }

        iosMain.dependencies {
            // Ktor
            ktor(platform = Platform.Ios)

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            // Ktor
            ktor(platform = Platform.Desktop)

        }

        wasmJsMain.dependencies {
            // Ktor
            ktor(platform = Platform.WasmJs)
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

    // KSP support for Room Compiler.
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)

    //Testing
    debugImplementation(compose.uiTooling)

}


private enum class Platform {
    Common, Ios, Android, Desktop, WasmJs
}

private fun KotlinDependencyHandler.ktor(
    platform: Platform
) {
    when (platform) {
        Platform.Ios -> implementation(libs.ktor.client.darwin)
        Platform.Android -> implementation(libs.ktor.client.okhttp)
        Platform.Desktop -> implementation(libs.ktor.client.okhttp)
        Platform.WasmJs -> implementation(libs.ktor.client.js)
        Platform.Common -> {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.json)
        }
    }
}

private fun KotlinDependencyHandler.datastore() {
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)
}

private fun KotlinDependencyHandler.coil() {
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}

room {
    schemaDirectory("$projectDir/schemas")
}