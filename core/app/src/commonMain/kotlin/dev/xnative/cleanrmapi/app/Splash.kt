package dev.xnative.cleanrmapi.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Temporary startup destination displayed while app wiring is initialized.
 */
@Serializable
object Splash : NavKey {

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
