package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import kotlinx.coroutines.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.Pair



@Preview @Composable fun MyUBoxPreview() = MyLayoutExample1(UBOX)
@Preview @Composable fun MyURowPreview() = MyLayoutExample1(UROW)
@Preview @Composable fun MyUColumnPreview() = MyLayoutExample1(UCOLUMN)

@OptIn(ExperimentalAnimationApi::class)
@Preview @Composable fun MyAnimatedContentPreview() {
    val type by produceState(initialValue = UBOX) {
        val types = UContainerType.values()
        for (i in 1..20) {
            delay(2000)
            value = types[i % 3]
        }
    }
    AnimatedContent(
        targetState = type,
        transitionSpec = { fadeIn(tween(900, delayMillis = 90)) with fadeOut(tween(900, delayMillis = 90)) }
    ) { target -> MyLayoutExample1(target) }
}

@Composable
fun MyLayoutExample1(type: UContainerType = UBOX) {

    val reports = remember { mutableStateListOf<Pair<String, Any>>() }
    fun report(v: Pair<String, Any>) {
        Log.i("kkk", "${v.first}: ${v.second}")
        reports.add(v)
    }

    Column(Modifier
        .fillMaxWidth()
        .background(Color.LightGray)) {
        Box(Modifier
            .border(4.dp, Color.Blue)
            .padding(4.dp)
            .requiredSize(360.dp)) {
            MyExaminedLayout1(
                type = type,
                withBox1Cyan = true,
                withBox2Red = true,
                withBox3Green = true,
                withBox4Blue = false,
                report = ::report,
            )
        }
        ReportsUi(Modifier.height(400.dp), reports)
    }
}

@Composable
fun MyExaminedLayout1(
    type: UContainerType = UBOX,
    withBox1Cyan: Boolean = false,
    withBox2Red: Boolean = false,
    withBox3Green: Boolean = false,
    withBox4Blue: Boolean = false,
    report: (Pair<String, Any>) -> Unit,
) {
    UAlign(USTART, USTART) {
        when (type) {
            UBOX -> UBasicBox { MyExaminedLayout1Content(withBox1Cyan, withBox2Red, withBox3Green, withBox4Blue, report) }
            UROW -> UBasicRow { MyExaminedLayout1Content(withBox1Cyan, withBox2Red, withBox3Green, withBox4Blue, report) }
            UCOLUMN -> UBasicColumn { MyExaminedLayout1Content(withBox1Cyan, withBox2Red, withBox3Green, withBox4Blue, report) }
        }
    }
}

@Composable
private fun MyExaminedLayout1Content(
    withBox1Cyan: Boolean = false,
    withBox2Red: Boolean = false,
    withBox3Green: Boolean = false,
    withBox4Blue: Boolean = false,
    report: (Pair<String, Any>) -> Unit,
) {
    if (withBox1Cyan) UAlign(USTART, UEND) { ExamBasicBox("magenta 160dp box", Color.Cyan, 160.dp.square, report = report) }
    if (withBox2Red) UAlign(UCENTER, UCENTER) { ExamBasicBox("red 80dp box", Color.Red, 80.dp.square, sizeRequired = true, report = report) }
    if (withBox3Green) UAlign(UEND, UEND) { ExamBasicBox("green 60dp box", Color.Green, 60.dp.square, report = report) }
    if (withBox4Blue) UAlign(USTRETCH, USTRETCH) { ExamBasicBox("blue 30dp stretched box", Color.Blue, 30.dp.square, report = report) }
}

val Dp.square get() = DpSize(this, this)

@Composable
fun ExamBasicBox(
    tag: String,
    color: Color = Color.Gray,
    size: DpSize = 100.dp.square,
    sizeRequired: Boolean = false,
    report: (Pair<String, Any>) -> Unit = { println("${it.first} ${it.second.str}") },
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
                    Box(Modifier.weight(1f)) { Text(data.str) }
                }
            }
        }
    }
}

data class PlaceInfo(val width: Int, val height: Int, val measuredWidth: Int, val measuredHeight: Int)

val Placeable.info get() = PlaceInfo(width, height, measuredWidth, measuredHeight)

private val Any?.str: String get() = when (this) {
    is Placeable -> "Placeable(width=$width, height=$height, measuredWidth=$measuredWidth, measuredHeight=$measuredHeight)"
    is String -> this
    else -> toString()
}

fun Modifier.reportMeasuring(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = layout { measurable, constraints ->
    report("$tag incoming constraints" to constraints)
    val placeable = measurable.measure(constraints)
    report("$tag measured placeable" to placeable.info)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}