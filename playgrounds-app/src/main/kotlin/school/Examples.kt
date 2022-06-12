package pl.mareklangiewicz.school

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*

@Composable
fun School() = Column {
    var srednica by remember { mutableStateOf(100f) }
    val animowanaSrednica by animateFloatAsState(targetValue = srednica)
    Column {
        val drugie = wlacznik(nazwa = "Drugie kolo")
        val razy2 = wlacznik(nazwa = "Razy 2")
        val razy3 = wlacznik(nazwa = "Razy 3")
        srednica = 100f * (if(razy2) 2 else 1) * (if(razy3) 3 else 1)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.Blue, animowanaSrednica / 2)
            if (drugie)
                drawCircle(Color.Blue, animowanaSrednica / 2, center + Offset(animowanaSrednica * 2, -animowanaSrednica * animowanaSrednica * 0.005f))
        }
    }
}

@Composable fun wlacznik(nazwa: String, init: Boolean = false): Boolean {
    var wlaczony by remember { mutableStateOf(init) }
    val color = if (wlaczony) Color.Blue else Color.Gray
    val modifier = Modifier
        .clickable { wlaczony = !wlaczony }
        .padding(2.dp)
        .border(2.dp, color)
        .padding(4.dp)
    Text(nazwa, modifier, color, style = MaterialTheme.typography.labelMedium)
    return wlaczony
}

@Preview
@Composable fun SchoolPreview() = School()