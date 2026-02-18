package dev.xnative.cleanrmapi.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.xnative.cleanrmapi.app.router.AppRouter
import dev.xnative.cleanrmapi.characters.navigation.CharactersGraph
import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesGraph
import dev.xnative.cleanrmapi.navigation.Graph
import dev.xnative.cleanrmapi.navigation.Destination
import dev.xnative.cleanrmapi.navigation.Router
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.koinInject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@Composable
private fun getConfig(
    graphs: List<Graph>
) = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            graphs.forEach {
                it.subclasses()
            }
            subclass(Splash::class, Splash.serializer())
        }
    }
}

@Composable
fun App() {


    val graphs: List<Graph> = listOf(
        koinInject<CharactersGraph>(),
        koinInject<EpisodesGraph>()
    )

    val backStack = rememberNavBackStack(
        configuration = getConfig(graphs),
        Splash
    )

    var router: Router? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        val appRouter = AppRouter(backStack)
        loadKoinModules(module { single<Router> { appRouter } })
        delay(300)
        router = appRouter
    }

    AnimatedContent(
        targetState = router
    ) { router ->

        router?.let { router ->

            val charactersNavigation: CharactersNavigation = koinInject()

            LaunchedEffect(Unit) {
                charactersNavigation.showCharacters()
                backStack.removeFirstOrNull()
            }

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
                            is Destination -> key.Screen(router)
                            else -> Text("wat")
                        }
                    }
                }
            )
        } ?: Splash.Screen()

    }

}

@Serializable
private object Splash : NavKey {

    @Composable
    fun Screen() {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "C'est le splash screen ici on s'amuse"
            )

        }
    }


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
