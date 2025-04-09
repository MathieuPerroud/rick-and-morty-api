package org.mathieu.cleanrmapi.ui.core

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Destination(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    object Characters: Destination(route = "characters")
    class CharacterDetails(characterId: String = "{characterId}"):
        Destination(
            route = "characterDetail/$characterId",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        )

    class EpisodeDetails(episodeId: String = "{episodeId}"):
        Destination(
            route = "episodeDetails/$episodeId",
            arguments = listOf(navArgument("episodeId") { type = NavType.IntType })
        )

    class LocationDetails(locationId: String = "{locationId}"):
            Destination(
                route = "locationDetails/$locationId",
                arguments = listOf(navArgument("locationId") { type = NavType.IntType})
            )
}

fun NavGraphBuilder.composable(
    destination: Destination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(
    route = destination.route,
    arguments = destination.arguments,
    deepLinks = deepLinks,
    content = content
)

fun NavController.navigate(
    destination: Destination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) = navigate(
    route = destination.route,
    navOptions = navOptions,
    navigatorExtras = navigatorExtras
)