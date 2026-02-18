rootProject.name = "CleanRmApiUDF"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core:app")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:navigation")
include(":core:presentation")
include(":features:characters:api")
include(":features:characters:data")
include(":features:characters:domain")
include(":features:characters:navigation")
include(":features:characters:presentation")
include(":features:episodes:api")
include(":features:episodes:data")
include(":features:episodes:domain")
include(":features:episodes:navigation")
include(":features:episodes:presentation")
