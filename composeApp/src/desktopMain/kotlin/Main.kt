import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.xnative.cleanrmapi.initKoin
import dev.xnative.cleanrmapi.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Clean RmApi UDF",
    ) {
        initKoin()
        App()
    }
}