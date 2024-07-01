package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import pl.mareklangiewicz.uwidgets.*

@Composable
fun MySimpleAssets(path: String) {
    var values by remember { mutableStateOf(emptyList<Pair<String, String>>()) }
    val assets = LocalContext.current.assets
    LaunchedEffect(path, assets) {
        values = withContext(Dispatchers.IO) {
            assets.list(path).orEmpty().map { name ->
                // val content = assets.open("$path/$name").bufferedReader().use { it.readLine().take(300) }
                val content = assets.open("$path/$name").bufferedReader().use { it.readText() }
                // TODO_later: make sure we show nicely short, empty, and longer files
                // (I might configure adding git status or other longer reports to these assets)
                name to content
            }
        }
    }
    Column {
        for ((name, content) in values) UColumn {
            UText(name)
            UText(content, bold = true, mono = true, maxLines = 1000)
        }
    }
}
