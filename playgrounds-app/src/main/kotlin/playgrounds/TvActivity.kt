package playgrounds

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.runtime.*
import pl.mareklangiewicz.playgrounds.*
import pl.mareklangiewicz.school.*

class TvActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TvContent() }
    }
}

@Composable
fun TvContent() {
    School()
}
