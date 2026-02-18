package dev.xnative.cleanrmapi.characters.presentation.navigation

import dev.xnative.cleanrmapi.characters.navigation.CharactersGraph
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetails
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersList
import dev.xnative.cleanrmapi.navigation.Destination
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class CharactersGraphImpl : CharactersGraph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<Destination>)
    override fun subclasses() = with(polymorphicModuleBuilder) {
        subclass(
            CharactersList::class,
            CharactersList.serializer()
        )
        subclass(
            CharacterDetails::class,
            CharacterDetails.serializer()
        )
    }

}

