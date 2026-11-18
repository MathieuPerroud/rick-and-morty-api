import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("io.realm.kotlin")
    kotlin("plugin.serialization")
}

android {
    namespace = "org.mathieu.projet2"
    compileSdk = 36

    defaultConfig {
        applicationId = "org.mathieu.projet2"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Keep the Kotlin JVM target aligned with the Java one declared in `android.compileOptions`,
// and stay on language version 1.9 so the Realm compiler plugin (io.realm.kotlin 3.0.0) passes
// the compiler's plugin language-version compatibility check.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
    compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_1_9)
}

val koinVersion: String by project
val ktorVersion: String by project
val realmVersion: String by project
val coroutinesVersion: String by project

dependencies {

    //Android
    implementation("androidx.core:core-ktx:1.12.0")
    // lifecycle-runtime-ktx 2.7.0: newest proven in versions/2_action_and_navigation_room_retrofit
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    // datastore 1.2.0: newest proven in main:gradle/libs.versions.toml
    implementation("androidx.datastore:datastore-preferences:1.2.0")
    implementation("androidx.datastore:datastore:1.2.0")

    //Compose
    // activity-compose 1.12.2: newest proven in main:gradle/libs.versions.toml
    implementation("androidx.activity:activity-compose:1.12.2")
    implementation(platform("androidx.compose:compose-bom:2025.12.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    // material-icons-core is no longer a transitive dependency of material3: the BOM pins it.
    implementation("androidx.compose.material:material-icons-core")
    //Navigation3 (versions verbatim from main's gradle/libs.versions.toml)
    implementation("org.jetbrains.androidx.navigation3:navigation3-ui:1.0.0-alpha06")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-navigation3:2.10.0-alpha08")

    // Coil 2.6.0: newest proven version on the io.coil-kt (coil2) coordinates
    // (versions/2_action_and_navigation_room_retrofit). Main's coil 3.3.0 lives on the
    // io.coil-kt.coil3 coordinates with the coil3 package names and is therefore a source
    // migration, not a version bump: blocked in this pass (documented in w6-report.md).
    implementation("io.coil-kt:coil:2.6.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-gif:2.6.0")


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

    //JetBrains
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    // kotlinx-serialization-json 1.8.0: newest proven version readable by the frozen Kotlin
    // 2.0.21 toolchain (main's 1.10.0 is compiled with Kotlin 2.3.0 — metadata 2.3.0 — and is
    // blocked; see w6-report.md). 1.8.0 is be57848's proven version.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.12.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}
