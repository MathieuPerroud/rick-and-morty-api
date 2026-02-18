import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.xnative.cleanrmapi.app.App
import dev.xnative.cleanrmapi.app.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Clean RmApi UDF",
    ) {
        initKoin()
        App()
    }
}