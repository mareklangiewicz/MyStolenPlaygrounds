package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*
import kotlin.Pair


@Composable
fun MyLayoutExample1() {

    val reports = remember { mutableStateListOf<Pair<String, Any>>() }
    fun report(v: Pair<String, Any>) {
        Log.i("kkk", "${v.first}: ${v.second}")
        reports.add(v)
    }

    Column(Modifier.fillMaxSize().background(Color.LightGray)) {
        Box(Modifier.requiredSize(400.dp)) { MyExaminedLayout(::report) }
        ReportsUi(reports)
    }
}

@Composable fun ReportsUi(reports: List<Pair<String, Any>>) {
    CompositionLocalProvider(LocalDensity provides Density(1f)) {
        Column {
            for ((key, data) in reports) {
                Row(Modifier
                    .padding(2.dp)) {
                    Box(Modifier.width(300.dp)) { Text(key) }
                    Box(Modifier.weight(1f)) { Text(data.shortInfo) }
                }
            }
        }
    }
}

private val Any.shortInfo: String get() = when {
    this is Placeable -> "Placeable(width=$width, height=$height)"
    else -> this.toString()
}

@Composable
private fun MyExaminedLayout(report: (Pair<String, Any>) -> Unit) {
    UAlign(USTART, USTART) {
        UBasicColumn {
            // Box(Modifier.background(Color.Cyan))
            UAlign(UEND, UEND) {
                UBasicBox {
                    Box(Modifier
                        .reportMeasuring("green box", report)
                        .background(Color.Green)
                        .size(20.dp)
                        .reportMeasuring("green box sized", report)
                    )
                }
            }
            UAlign(USTART, UEND) {
                UBasicBox {
                    Box(Modifier
                        .background(Color.Magenta)
                        .size(140.dp))
                }
            }
            UAlign(UCENTER, UCENTER) {
                UBasicBox {
                    Box(Modifier
                        .background(Color.Red)
                        .requiredSize(110.dp))
                }
            }
            // UAlign(USTRETCH, USTRETCH) { UBasicBox { Box(Modifier.background(Color.Blue).size(20.dp)) } }
        }
    }
}

fun Modifier.reportMeasuring(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = layout { measurable, constraints ->
    report("$tag incoming constraints" to constraints)
    val placeable = measurable.measure(constraints)
    report("$tag measured placeable" to placeable)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}


@Preview @Composable fun MyLayoutPreview() = MyLayoutExample1()