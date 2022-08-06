package pl.mareklangiewicz.playgrounds

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.integration.demos.common.*
import androidx.compose.material3.*
import androidx.compose.material3.catalog.library.*
import androidx.compose.material3.demos.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.school.*
import pl.mareklangiewicz.udemo.*
import pl.mareklangiewicz.uwidgets.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }
}

@Composable
private fun MainContent() {
    val density = LocalDensity.current
    val scale = 0.7f
    // val scale = 1f
    CompositionLocalProvider(LocalDensity provides Density(density.density * scale, density.fontScale)) {
        UTabs(
            "UDemo" to { UDemo() },
            "School" to { School() },
            "Playgrounds" to {
                UTabs(
                    "My Stolen Playgrounds" to {
                        PlaygroundsTheme {
                            Surface(color = Color.White) {
                                Playgrounds()
                            }
                        }
                    },
                    "TODO: Other playgrounds??" to { Text("TODO") }
                )
            },
            "Material3" to {
                UTabs(
                    "Material 3 Catalog" to { Material3CatalogApp() },
                    "Material 3 Demos" to { MyDemosSelector(Material3Demos) },
                )
            }
        )
    }
}

@Composable
private fun MyDemosSelector(demos: DemoCategory) {
    val contents = demos.allLaunchableDemos().filterIsInstance<ComposableDemo>().map { demo ->
        val content: @Composable () -> Unit = { demo.content {} }
        demo.title to content
    }
    UTabs(*contents.toTypedArray())
}
