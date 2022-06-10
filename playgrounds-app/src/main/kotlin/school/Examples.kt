package pl.mareklangiewicz.school

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*

@Composable
fun School() {
    var srednica by remember { mutableStateOf(0f) }
    val animowanaSrednica by animateFloatAsState(targetValue = srednica)
    Column {
        Button(onClick = { srednica = 100f }) { Text("Ustaw srednicę na 100") }
        Button(onClick = { srednica = 50f }) { Text("Ustaw srednicę na 50") }
        Button(onClick = { srednica = 200f }) { Text("Ustaw srednicę na 200") }
        Canvas(modifier = Modifier.fillMaxSize()) {
            // drawCircle(Color.Blue, srednica / 2)
            drawCircle(Color.Blue, animowanaSrednica / 2)
            // drawCircle(Color.hsv(animowanaSrednica, 1f, 1f), animowanaSrednica / 2)
            drawCircle(Color.Blue, animowanaSrednica / 2, center + Offset(animowanaSrednica * 3, -animowanaSrednica * animowanaSrednica * 0.02f))
        }
    }
}
