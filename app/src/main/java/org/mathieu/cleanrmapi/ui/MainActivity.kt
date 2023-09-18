package org.mathieu.cleanrmapi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.mathieu.cleanrmapi.ui.core.theme.LeTheme
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsScreen
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = { MainContent() }
                )
            }
        }
    }
}

@Composable
private fun MainContent() {

    val navController = rememberNavController()

    //https://developer.android.com/jetpack/compose/navigation?hl=fr
    NavHost(navController = navController, startDestination = "characters") {

        composable("characters") { CharactersScreen(navController) }

        composable(
            route = "characterDetail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->

            CharacterDetailsScreen(
                navController = navController,
                id = backStackEntry.arguments?.getInt("characterId") ?: -1
            )

        }

    }

}
