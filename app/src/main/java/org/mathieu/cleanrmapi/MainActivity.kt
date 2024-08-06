package org.mathieu.cleanrmapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.mathieu.characters.details.CharacterDetailsScreen
import org.mathieu.characters.list.CharactersScreen
import org.mathieu.ui.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            org.mathieu.ui.theme.LeTheme {
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

        composable(org.mathieu.ui.Destination.Characters) {
            org.mathieu.characters.list.CharactersScreen(
                navController
            )
        }

        composable(
            destination = org.mathieu.ui.Destination.CharacterDetails()
        ) { backStackEntry ->

            org.mathieu.characters.details.CharacterDetailsScreen(
                navController = navController,
                id = backStackEntry.arguments?.getInt("characterId") ?: -1
            )

        }

    }

}
