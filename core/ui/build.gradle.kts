plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val compileVersion: String by project
val minVersion: String by project

android {
    namespace = "org.mathieu.ui"
    compileSdk = compileVersion.toInt()

    defaultConfig {
        minSdk = minVersion.toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    // Compose part
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    // JVM Part
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

val navVersion: String by project
val koinVersion: String by project
val coroutinesVersion: String by project

dependencies {

    //Projects
    implementation(project(":core:domain"))

    //Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //Compose
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Coil
    implementation("io.coil-kt:coil:2.3.0")
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("io.coil-kt:coil-gif:2.3.0")


    //Koin
    implementation( "io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    //JetBrains
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

}