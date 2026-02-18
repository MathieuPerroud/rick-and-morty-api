@file:Suppress("UnstableApiUsage")

package dev.xnative

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)
public fun Project.kmpLibrary(
    common: KotlinDependencyHandler.() -> Unit = { },
    nonJs: KotlinDependencyHandler.() -> Unit = { },
    mobile: KotlinDependencyHandler.() -> Unit = { },
    android: KotlinDependencyHandler.() -> Unit = { },
    ios: KotlinDependencyHandler.() -> Unit = { },
    desktop: KotlinDependencyHandler.() -> Unit = { },
    wasmJs: KotlinDependencyHandler.() -> Unit = { },
) {
    val kotlin = extensions.getByType<KotlinMultiplatformExtension>()

    with(kotlin) {

        jvmToolchain(21)

        fun Project.modulePascalCaseName(): String =
            path.split(":")
                .filter { it.isNotBlank() }
                .joinToString("") { it.replaceFirstChar(Char::uppercaseChar) }

        val iosTargets = listOf(iosArm64(), iosSimulatorArm64())
        val moduleFrameworkBaseName = project.modulePascalCaseName()

        iosTargets.forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = moduleFrameworkBaseName
                isStatic = true
                freeCompilerArgs += listOf("-Xbinary=bundleId=dev.xnative")
            }
        }

        jvm("desktop")
        wasmJs()

        sourceSets.apply {

            all {
                languageSettings.enableLanguageFeature("ContextParameters")
            }

            val commonMain = maybeCreate("commonMain")

            // Intermediate buckets (explicit dependsOn = reliable with Kotlin 2.x + Android KMP)
            val nonJsMain = maybeCreate("nonJsMain").apply { dependsOn(commonMain) }
            val mobileMain = maybeCreate("mobileMain").apply { dependsOn(nonJsMain) }
            val iosMain = maybeCreate("iosMain").apply { dependsOn(mobileMain) }

            // Platform source sets (existence depends on enabled targets)
            findByName("androidMain")?.apply { dependsOn(mobileMain) }
                ?: maybeCreate("androidMain").apply { dependsOn(mobileMain) }

            findByName("desktopMain")?.apply { dependsOn(nonJsMain) }
                ?: maybeCreate("desktopMain").apply { dependsOn(nonJsMain) }

            // Wire iOS leaf sets if present
            findByName("iosArm64Main")?.apply { dependsOn(iosMain) }
            findByName("iosSimulatorArm64Main")?.apply { dependsOn(iosMain) }
            findByName("iosX64Main")?.apply { dependsOn(iosMain) } // optional

            // Wire wasm leaf set
            val wasmJsMain = maybeCreate("wasmJsMain")

            // Apply deps
            commonMain.apply {
                dependencies(common)
            }
            nonJsMain.apply {
                dependencies(nonJs)
            }
            mobileMain.apply {
                dependencies(mobile)
            }
            findByName("androidMain")?.apply {
                dependencies(android)
            }
            iosMain.apply {
                dependencies(ios)
            }
            findByName("desktopMain")?.apply {
                dependencies(desktop)
            }
            wasmJsMain.apply {
                dependencies(wasmJs)
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
        compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_2_3)
        if (name.contains("ios", ignoreCase = true)) {
            compilerOptions.freeCompilerArgs.add("-Xskip-metadata-version-check")
        }
    }

}
