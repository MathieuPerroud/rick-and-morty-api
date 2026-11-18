import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // No `org.jetbrains.kotlin.android`: AGP 9's built-in Kotlin compiles this module
    // (developer.android.com/build/migrate-to-built-in-kotlin).
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

android {
    namespace = "org.mathieu.projet2"
    // compileSdk 37: required by the latest androidx set (core 1.19.1, lifecycle 2.11.0,
    // compose 1.12.1, navigation3 1.2.0 all declare minCompileSdk 37 in their AAR metadata).
    compileSdk = 37

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
        // Java 17: the current androidx/compose/navigation3 set ships bytecode built with
        // JVM target 11+ and the Kotlin compiler refuses to inline it into 1.8 bytecode
        // ("Cannot inline bytecode built with JVM target 11 into bytecode that is being
        // built with JVM target 1.8").
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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

// Keep the Kotlin JVM target aligned with the Java one declared in `android.compileOptions`.
// (With AGP 9 built-in Kotlin the default is already `android.compileOptions.targetCompatibility`;
// this block keeps the value explicit and consistent for every Kotlin compile task.)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
}

val koinVersion: String by project
val ktorVersion: String by project
val coroutinesVersion: String by project
val roomVersion: String by project

dependencies {

    //Android
    implementation("androidx.core:core-ktx:1.19.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    implementation("androidx.datastore:datastore-preferences:1.3.0")
    implementation("androidx.datastore:datastore:1.3.0")

    //Compose
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    // material-icons-core is no longer a transitive dependency of material3: the BOM pins it.
    implementation("androidx.compose.material:material-icons-core")
    //Navigation3 (latest stable on Maven Central: no preview taken)
    implementation("org.jetbrains.androidx.navigation3:navigation3-ui:1.2.0")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-navigation3:2.11.0")

    // Coil 3.6.3 (io.coil-kt.coil3): latest stable on Maven Central (coil3/coil-compose
    // metadata), with the Coil 2 -> 3 source migration (package coil -> coil3). Coil 3's
    // core no longer fetches from the network: coil-network-ktor3 restores it and reuses the
    // Ktor CIO engine already on this module's classpath (coil-kt.github.io/coil/network/).
    implementation("io.coil-kt.coil3:coil:3.6.3")
    implementation("io.coil-kt.coil3:coil-compose:3.6.3")
    implementation("io.coil-kt.coil3:coil-gif:3.6.3")
    implementation("io.coil-kt.coil3:coil-network-ktor3:3.6.3")


    //Koin
    implementation( "io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    //Ktor
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    //Room (coordinates verbatim from versions/2_action_and_navigation_room_retrofit:app/build.gradle.kts)
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //JetBrains
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    // kotlinx-serialization-json 1.13.0: latest stable on Maven Central; its Kotlin 2.3.0
    // metadata floor (w6-report.md blocker) is satisfied by Kotlin 2.4.21.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.13.0")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}
