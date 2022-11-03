package pl.mareklangiewicz.playgrounds

import android.content.*
import android.net.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
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
fun MainContent() {
    val density = LocalDensity.current
    // val newdd = 1f
    val newdd = 2f
    // val newdd = density.density
    // val newdd = 0.7f * density.density
    val context = LocalContext.current
    CompositionLocalProvider(LocalDensity provides Density(newdd, density.fontScale)) {
        UTabs(
            "UDemo" to { UDemo() },
            "Playgrounds" to {
                UTabs(
                    "My Stolen Playgrounds (show on click)" to {
                        PlaygroundsTheme {
                            Surface(color = Color.White) {
                                Playgrounds()
                            }
                        }
                    },
                    "My Stolen Playgrounds (show in grid)" to {
                        PlaygroundsTheme {
                            Surface(color = Color.White) {
                                CompositionLocalProvider(LocalFancyItemShowContent provides true) { Playgrounds() }
                            }
                        }

                    },
                    "School" to { School() },
                )
            },
            "Material3" to {
                UTabs(
                    "Material 3 Catalog" to { Material3CatalogApp() },
                    "Material 3 Demos" to { MyDemosSelector(Material3Demos) },
                )
            },
            "Browser" to {
                Row {
                    Button({ context.launchBrowser("https://google.com") }) { Text("Google") }
                    Button({ context.launchBrowser("https://github.com") }) { Text("Github") }
                    Button({ context.launchBrowser("https://mareklangiewicz.pl") }) { Text("Marek") }
                }
            }
        )
    }
}

private fun Context.launchBrowser(url: String) {
    val intent = CustomTabsIntent.Builder().build()
    intent.launchUrl(this, Uri.parse(url))
}

@Composable
private fun MyDemosSelector(demos: DemoCategory) {
    val contents = demos.allLaunchableDemos().filterIsInstance<ComposableDemo>().map { demo ->
        val content: @Composable () -> Unit = { demo.content {} }
        demo.title to content
    }
    UTabs(*contents.toTypedArray())
}
