package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pl.mareklangiewicz.school.School

fun main() = application {
  Window(onCloseRequest = ::exitApplication, title = "MyStolenPlaygrounds (desktop)") { App() }
}

/**
 * Everything on this screen comes from :playgrounds-basic's commonMain -- the SAME source the
 * android app renders. That is the point of the module: it is the jvm entry point and a window,
 * not a second copy of the UI.
 */
@Composable
private fun App() = PlaygroundsTheme {
  Surface {
    Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
      Text("MyStolenPlaygrounds on the desktop", style = MaterialTheme.typography.headlineSmall)
      Spacer(Modifier.height(16.dp))
      MyFancyFrameTheme {
        MyFancyFrame(title = "school/Examples.kt", onClick = { println("click") }) { School() }
      }
    }
  }
}
