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
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.Pair



@Preview @Composable fun MyUBoxPreview() = MyLayoutExample1(UBOX)
@Preview @Composable fun MyURowPreview() = MyLayoutExample1(UROW)
@Preview @Composable fun MyUColumnPreview() = MyLayoutExample1(UCOLUMN)

@Composable
fun MyLayoutExample1(type: UContainerType = UBOX) {

    val reports = remember { mutableStateListOf<Pair<String, Any>>() }
    fun report(v: Pair<String, Any>) {
        Log.i("kkk", "${v.first}: ${v.second}")
        reports.add(v)
    }

    Column(Modifier.fillMaxWidth().background(Color.LightGray)) {
        Box(Modifier.border(4.dp, Color.Blue).padding(4.dp).requiredSize(360.dp)) {
            MyExaminedLayout1(type, report = ::report)
        }
        ReportsUi(Modifier.height(400.dp), reports)
    }
}

@Composable
fun MyExaminedLayout1(type: UContainerType = UBOX, addStretchedBlueBox: Boolean = false, report: (Pair<String, Any>) -> Unit) {
    UAlign(USTART, USTART) {
        when (type) {
            UBOX -> UBasicBox { MyExaminedLayout1Content(addStretchedBlueBox, report) }
            UROW -> UBasicRow { MyExaminedLayout1Content(addStretchedBlueBox, report) }
            UCOLUMN -> UBasicColumn { MyExaminedLayout1Content(addStretchedBlueBox, report) }
        }
    }
}

@Composable
private fun MyExaminedLayout1Content(addStretchedBlueBox: Boolean = false, report: (Pair<String, Any>) -> Unit) {
    UAlign(USTART, UEND) { ExamBasicBox("magenta 160dp box", Color.Magenta, 160.dp.square, report = report) }
    UAlign(UCENTER, UCENTER) { ExamBasicBox("red 80dp box", Color.Red, 80.dp.square, sizeRequired = true, report = report) }
    UAlign(UEND, UEND) { ExamBasicBox("green 60dp box", Color.Green, 60.dp.square, report = report) }
    if (addStretchedBlueBox) UAlign(USTRETCH, USTRETCH) { ExamBasicBox("blue 30dp stretched box", Color.Blue, 30.dp.square, report = report) }
}

val Dp.square get() = DpSize(this, this)

@Composable
fun ExamBasicBox(
    tag: String,
    color: Color = Color.Gray,
    size: DpSize = 100.dp.square,
    sizeRequired: Boolean = false,
    report: (Pair<String, Any>) -> Unit = { println("${it.first} ${it.second.shortInfo}") },
) {
    UBasicBox {
        Box(Modifier
            .reportMeasuring("$tag outer", report)
            .background(color)
            .run { if (sizeRequired) requiredSize(size) else size(size) }
            .reportMeasuring("$tag inner", report)
        )
    }
}


@Composable fun ReportsUi(modifier: Modifier = Modifier, reports: List<Pair<String, Any>>) {
    CompositionLocalProvider(LocalDensity provides Density(1f)) {
        Column(modifier) {
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

private val Any.shortInfo: String get() = when (this) {
    is Placeable -> "Placeable(width=$width, height=$height, measuredWidth=$measuredWidth, measuredHeight=$measuredHeight)"
    is String -> this
    else -> toString()
}

fun Modifier.reportMeasuring(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = layout { measurable, constraints ->
    report("$tag incoming constraints" to constraints)
    val placeable = measurable.measure(constraints)
    report("$tag measured placeable" to placeable.shortInfo)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}