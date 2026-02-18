package dev.xnative.cleanrmapi.navigation

import kotlinx.serialization.modules.PolymorphicModuleBuilder

interface Graph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<Destination>)
    fun subclasses()

}