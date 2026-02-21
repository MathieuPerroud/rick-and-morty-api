package dev.xnative.cleanrmapi.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.PolymorphicModuleBuilder

interface Graph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<NavKey>)
    fun subclasses()

}