import dev.xnative.kmpLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("dev.xnative.kmp-library")
}

kmpLibrary(
    common = {
        implementation(libs.compose.runtime)
        implementation(project(":core:navigation"))
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.characters.navigation"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

    }
}
