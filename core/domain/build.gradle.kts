plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val compileVersion: String by project
val minVersion: String by project

android {
    namespace = "org.mathieu.domain"
    compileSdk = compileVersion.toInt()

    defaultConfig {
        minSdk = minVersion.toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
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

val koinVersion: String by project

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")

    //Koin
    implementation( "io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
}