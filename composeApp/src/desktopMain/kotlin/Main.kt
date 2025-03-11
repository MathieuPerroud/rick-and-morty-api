import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.mathieu.cleanrmapi.initKoin
import org.mathieu.cleanrmapi.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Clean RmApi UDF",
    ) {
        initKoin()
        App()
    }
}