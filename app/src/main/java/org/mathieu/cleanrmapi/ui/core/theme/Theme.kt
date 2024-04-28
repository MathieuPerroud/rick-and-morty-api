package org.mathieu.cleanrmapi.ui.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import org.mathieu.projet2.R

private val DarkColorScheme
    @Composable
    get() = darkColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.on_primary),
        secondary = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.on_secondary),
        background = colorResource(id = R.color.background),
        onBackground = colorResource(id = R.color.on_background),
        surface = colorResource(id = R.color.surface),
        onSurface = colorResource(id = R.color.on_surface),
    )

//Here we are using the legacy color system with flavored resources, we could have defined Colors
// directly into a `Colors.kt` file.
// Material ColorScheme is useful when we work with `.material3` related composables which have theme
// already applied to.
private val LightColorScheme
    @Composable
    get() = lightColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.on_primary),
        secondary = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.on_secondary),
        background = colorResource(id = R.color.background),
        onBackground = colorResource(id = R.color.on_background),
        surface = colorResource(id = R.color.surface),
        onSurface = colorResource(id = R.color.on_surface),
    )

@Composable
fun LeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}