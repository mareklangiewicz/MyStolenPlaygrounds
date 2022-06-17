package pl.mareklangiewicz.school

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*

@Composable
fun School() = Column {
    Column {
        val drugie = wlacznik(nazwa = "Drugie kolo", init = true)
        val razy2 = wlacznik(nazwa = "Razy 2")
        val razy3 = wlacznik(nazwa = "Razy 3")
        var liczba by remember { mutableStateOf(0f) }
        val ml = liczba * (if(razy2) 2 else 1) * (if(razy3) 3 else 1)
        val al by animateFloatAsState(targetValue = ml)
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(al - 100f) {
                drawArc(Color(1f-al/2000f, al/3000f, al/4000f, 0.2f), 0f, 130f, true)
                drawArc(Color(al/2000f, 1f-al/3000f, al/4000f, 0.2f), 888f, 130f, true)
                //blabla
            }
            drawCircle(Color.Blue, al / 2, alpha = al / 1500f)
            if (drugie)
                drawCircle(Color.Blue, al / 2, center + Offset(al * 2, -al * al * 0.001f), al / 1500f)
            rotate(al / 5f) {
                drawLine(Color.Cyan, Offset(100f, 550f), Offset(800f, 100f), 20f)
                scale(al / 500f) {
                    rotate(al) {
                        drawLine(Color.Green, Offset(100f, 550f), Offset(800f, 100f), 20f)
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            while (true) {
                delay(500)
                liczba += 10f
                if (liczba > 200f) liczba -= 200f
            }
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