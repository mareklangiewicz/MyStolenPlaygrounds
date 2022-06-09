package pl.mareklangiewicz.playgrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.integration.demos.common.*
import androidx.compose.material3.*
import androidx.compose.material3.catalog.library.*
import androidx.compose.material3.demos.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
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
    UTabs(
        "School" to { School() },
        "UDemo" to { UDemo() },
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

@Composable
private fun MyDemosSelector(demos: DemoCategory) {
    val contents = demos.allLaunchableDemos().filterIsInstance<ComposableDemo>().map { demo ->
        val content: @Composable () -> Unit = { demo.content {} }
        demo.title to content
    }
    UTabs(*contents.toTypedArray())
}
