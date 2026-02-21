package dev.xnative.cleanrmapi.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.PolymorphicModuleBuilder

/**
 * Registers all serializable navigation keys handled by one feature graph.
 *
 * The app aggregates several [Graph] implementations to build the polymorphic serializer
 * used by Navigation3 when persisting/restoring back stacks.
 */
interface Graph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<NavKey>)
    /**
     * Declares the [NavKey] subclasses handled by this graph.
     */
    fun subclasses()

}
