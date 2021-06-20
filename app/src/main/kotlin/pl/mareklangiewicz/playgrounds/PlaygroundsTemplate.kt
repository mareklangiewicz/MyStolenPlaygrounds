package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.playgrounds.ui.theme.PlaygroundsTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaygroundsTemplate() {
    Row {
        CompositionLocalProvider(LocalDensity provides Density(1.5f)) {
            LazyVerticalGrid(cells = GridCells.Adaptive(128.dp)) {
                item { Text("Some Sample") } // replace
                item { Text("Some Sample") } // remove
                item { Text("Some Sample") } // remove
                for (i in 1..15) item { HelloColumn() } // remove
                for (i in 1..15) item { Text("Some Sample $i") } // remove
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaygroundsTemplatePreview() {
    PlaygroundsTheme {
        PlaygroundsTemplate()
    }
}


@Preview
@Composable
fun HelloMultiDensity() {
    Row {
        HelloColumn()
        CompositionLocalProvider(LocalDensity provides Density(1f)) {
            HelloColumn()
        }
    }
}

@Composable
private fun HelloColumn() {
    Column {
        val d = LocalDensity.current.density
        for (a in 1..5)
            Text(text = "Hello $a d:$d")
    }
}
