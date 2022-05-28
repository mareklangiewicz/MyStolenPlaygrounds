package pl.mareklangiewicz.playgrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.integration.demos.common.*
import androidx.compose.material3.*
import androidx.compose.material3.catalog.library.*
import androidx.compose.material3.demos.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTextTabs(
                "Material 3 Catalog" to { Material3CatalogApp() },
                "Material 3 Demos" to { MyDemosSelector(Material3Demos) },
                "My Stolen Playgrounds" to {
                    PlaygroundsTheme {
                        Surface(color = Color.White) {
                            Playgrounds()
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun MyDemosSelector(demos: DemoCategory) {
    val contents = demos.allLaunchableDemos().filterIsInstance<ComposableDemo>().map { demo ->
        val content: @Composable () -> Unit = { demo.content {} }
        demo.title to content
    }
    UTextTabs(*contents.toTypedArray())
}

// TODO: Polish and move to UWidgets when it supports android platform.
@Composable
fun UTextTabs(vararg contents: Pair<String, @Composable () -> Unit>) {
    var currentIdx by remember { mutableStateOf(0) }
    Column {
        TabRow(selectedTabIndex = currentIdx) {
            contents.forEachIndexed { index, (title, _) ->
                Tab(
                    selected = currentIdx == index,
                    onClick = { currentIdx = index },
                    text = { Text(title) },
                )
            }
        }
        contents[currentIdx].second()
    }
}