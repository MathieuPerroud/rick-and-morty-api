package dev.xnative.cleanrmapi.characters.presentation.navigation

import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.characters.navigation.CharactersGraph
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.CharactersEntryScreen
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class CharactersGraphImpl : CharactersGraph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<NavKey>)
    override fun subclasses() = with(polymorphicModuleBuilder) {
        subclass(
            CharactersEntryScreen::class,
            CharactersEntryScreen.serializer()
        )
        subclass(
            CharactersListScreen::class,
            CharactersListScreen.serializer()
        )
        subclass(
            CharacterDetailsScreen::class,
            CharacterDetailsScreen.serializer()
        )
        subclass(
            CharactersDashboardScreen::class,
            CharactersDashboardScreen.serializer()
        )
    }

}
