plugins {
    id("com.android.library")
    id("io.realm.kotlin")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}

val compileVersion: String by project
val minVersion: String by project

android {
    namespace = "org.mathieu.data"
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
val ktorVersion: String by project
val realmVersion: String by project

dependencies {

    //Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    //Projects
    implementation(project(":core:domain"))

    //Koin
    implementation( "io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    //Ktor
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    //Realm
    implementation("io.realm.kotlin:library-base:$realmVersion")
}