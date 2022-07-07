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
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.uwidgets.*
import kotlin.Pair


fun Modifier.reportMeasuring(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = layout { measurable, constraints ->
    report("$tag measure with" to constraints)
    val placeable = measurable.measure(constraints)
    report("$tag measured" to placeable.data)
    layout(placeable.width, placeable.height) { placeable.place(0, 0) }
}

fun Modifier.reportPlacement(tag: String, report: (Report) -> Unit): Modifier = onPlaced {
    report("$tag placed" to it.data)
}

fun Modifier.reportMeasuringAndPlacement(tag: String, report: (Report) -> Unit): Modifier =
    reportMeasuring(tag, report).reportPlacement(tag, report)

// first is key, second is reported data
typealias Report = Pair<String, Any>

// rspek so I can filter logs with uspek/rspek/spek
class ReportsModel(val tag: String = "rspek") {
    val reports = mutableStateListOf<Report>()
    fun report(v: Pair<String, Any>) {
        Log.d(tag, "${v.first}: ${v.second.str}")
        reports.add(v)
    }
}

@Composable fun rememberReportsModel(tag: String = "rspek") = remember { ReportsModel(tag) }

@Composable fun ReportsUi(modifier: Modifier = Modifier, model: ReportsModel = rememberReportsModel()) = ReportsUi(modifier, model.reports)
@Composable fun ReportsUi(modifier: Modifier = Modifier, reports: List<Report>) {
    CompositionLocalProvider(LocalDensity provides Density(1f)) {
        Column(modifier) {
            reports.forEachIndexed { idx, (key, data) ->
                Row(
                    Modifier
                        .background(Color.White.darken(.1f * (idx % 3)))
                        .padding(2.dp)) {
                    Box(Modifier.width(400.dp)) { Text(key) }
                    Box(Modifier.weight(1f)) { Text(data.str) }
                }
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> Report.reported(key: String? = null, checkData: T.() -> Boolean = { true }) {
    if (key != null) check (first == key) { "Unexpected key: $first != $key"}
    val data = second as T
    check(data.checkData()) { "Unexpected data reported at: $key"}
}

fun Report.reportedPlacement(tag: String, checkData: LayoutCoordinatesData.() -> Boolean = { true }) =
    reported("$tag placed", checkData)

