package org.mathieu.cleanrmapi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.mathieu.cleanrmapi.ui.core.theme.LeTheme
import org.mathieu.cleanrmapi.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    // The scope NavDisplay host owns the back stack; there is no NavHost,
                    // rememberNavController, route string or navArgument left in the app.
                    content = { AppNavHost() }
                )
            }
        }
    }
}
