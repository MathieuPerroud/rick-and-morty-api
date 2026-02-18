import dev.xnative.kmpLibrary

plugins {
    id("dev.xnative.kmp-library")
    alias(libs.plugins.compose.compiler)
}

kmpLibrary(
    common = {
        implementation(libs.compose.runtime)
        implementation(libs.navigation3.ui)
    }
)

kotlin {
    android {
        namespace = "dev.xnative.cleanrmapi.core.navigation"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

    }

}
