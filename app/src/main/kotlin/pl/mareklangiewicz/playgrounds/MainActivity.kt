package pl.mareklangiewicz.playgrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import pl.mareklangiewicz.playgrounds.ui.theme.PlaygroundsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Playgrounds()
                }
            }
        }
    }
}

@Composable
fun Playgrounds() {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaygroundsTheme {
        Playgrounds()
    }
}