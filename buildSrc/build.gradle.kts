plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.10")
    implementation("com.android.tools.build:gradle:9.0.1")
}

gradlePlugin {
    plugins {
        create("kmpLibrary") {
            id = "dev.xnative.kmp-library"
            implementationClass = "dev.xnative.KmpLibraryPlugin"
        }
    }
}
