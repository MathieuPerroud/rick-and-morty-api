package dev.xnative

import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        project.pluginManager.apply("com.android.kotlin.multiplatform.library")
    }
}
