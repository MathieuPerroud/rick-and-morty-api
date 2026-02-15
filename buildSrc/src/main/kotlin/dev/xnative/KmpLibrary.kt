@file:Suppress("UnstableApiUsage")

package dev.xnative

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
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

        applyDefaultHierarchyTemplate {
            common {
                group("nonJs") {
                    group("mobile") {
                        withAndroidTarget()
                        group("ios") {
                            withIos()
                        }
                    }
                    withJvm()
                }
            }
        }

        sourceSets.apply {
            named<KotlinSourceSet>("commonMain") { dependencies(common) }
            named<KotlinSourceSet>("nonJsMain") { dependencies(nonJs) }
            named<KotlinSourceSet>("mobileMain") { dependencies(mobile) }
            named<KotlinSourceSet>("androidMain") { dependencies(android) }
            named<KotlinSourceSet>("iosMain") { dependencies(ios) }
            named<KotlinSourceSet>("desktopMain") { dependencies(desktop) }
            named<KotlinSourceSet>("wasmJsMain") { dependencies(wasmJs) }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
        compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
        if (name.contains("ios", ignoreCase = true)) {
            compilerOptions.freeCompilerArgs.add("-Xskip-metadata-version-check")
        }
    }
}
