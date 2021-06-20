package pl.mareklangiewicz.playgrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import pl.mareklangiewicz.playgrounds.ui.theme.PlaygroundsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PlaygroundsTemplate()
                }
            }
        }
    }
}

