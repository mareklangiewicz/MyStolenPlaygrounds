package pl.mareklangiewicz.playgrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    Text(text = "Hello Playgrounds!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaygroundsTheme {
        Playgrounds()
    }
}