package dev.xnative.cleanrmapi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.xnative.cleanrmapi.ui.core.CharacterDestination
import dev.xnative.cleanrmapi.ui.core.EpisodeDestination
import dev.xnative.cleanrmapi.ui.screens.characterdetails.CharacterDetailsScreen
import dev.xnative.cleanrmapi.ui.screens.characters.CharactersScreen
import dev.xnative.cleanrmapi.ui.screens.episodedetails.EpisodeDetailsScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(
                CharacterDestination.Characters::class,
                CharacterDestination.Characters.serializer()
            )
            subclass(
                CharacterDestination.CharacterDetails::class,
                CharacterDestination.CharacterDetails.serializer()
            )
            subclass(
                EpisodeDestination.EpisodeDetails::class,
                EpisodeDestination.EpisodeDetails.serializer()
            )
        }
    }
}

@Composable
fun App() {
    MainContent()
}

@Composable
private fun MainContent() {

    val backStack = rememberNavBackStack(config, CharacterDestination.Characters)


    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberLifecycleScopeNavEntryDecorator()
        ),
        entryProvider = { key ->
            NavEntry(key) {
                when (key) {
                    CharacterDestination.Characters -> CharactersScreen(backStack)
                    is CharacterDestination.CharacterDetails ->
                        CharacterDetailsScreen(
                            backStack = backStack,
                            id = key.characterId
                        )

                    is EpisodeDestination.EpisodeDetails ->
                        EpisodeDetailsScreen(
                            backStack = backStack,
                            id = key.episodeId
                        )

                }
            }

        }
    )

}

@Composable
private fun <T : Any> rememberLifecycleScopeNavEntryDecorator(): LifecycleScopeNavEntryDecorator<T> =
    remember { LifecycleScopeNavEntryDecorator() }

private class LifecycleScopeNavEntryDecorator<T : Any>() :
    NavEntryDecorator<T>(
        decorate = { entry ->
            val viewModelStore = remember { ViewModelStore() }

            CompositionLocalProvider(
                value = LocalViewModelStoreOwner provides object : ViewModelStoreOwner {
                    override val viewModelStore = viewModelStore
                },
                content = { entry.Content() }
            )
        },
    )
