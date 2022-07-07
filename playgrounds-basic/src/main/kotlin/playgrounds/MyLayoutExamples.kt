package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.geometry.Size
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
import kotlin.math.*


@Preview @Composable fun MyUBoxPreview() = MyLayoutExample1(UBOX)
@Preview @Composable fun MyURowPreview() = MyLayoutExample1(UROW)
@Preview @Composable fun MyUColumnPreview() = MyLayoutExample1(UCOLUMN)

@OptIn(ExperimentalAnimationApi::class)
@Preview @Composable fun MyAnimatedContentPreview() {
    val type by produceState(initialValue = UBOX) {
        val types = UContainerType.values()
        for (i in 1..200) {
            delay(2000)
            value = types[i % 3]
        }
    }
    AnimatedContent(
        targetState = type,
        transitionSpec = { fadeIn(tween(900, easing = LinearEasing)) with fadeOut(tween(900, easing = LinearEasing)) }
    ) { MyLayoutExample1(it) }
}

@Composable fun MyLayoutExample1(type: UContainerType = UBOX) {

    val reports = remember { mutableStateListOf<Pair<String, Any>>() }
    fun report(v: Pair<String, Any>) {
        Log.i("kkk", "${v.first}: ${v.second}")
        reports.add(v)
    }

    Column(Modifier.fillMaxWidth()) {
        MyExaminedLayout1(
            type = type,
            withBox1Cyan = true,
            withBox2Red = true,
            withBox3Green = true,
            withBox4Blue = false,
            report = ::report,
        )
        ReportsUi(Modifier.height(400.dp), reports)
    }
}

@Composable fun MyExaminedLayout1(
    type: UContainerType = UBOX,
    size: DpSize = 400.dp.square,
    withBox1Cyan: Boolean = false,
    withBox2Red: Boolean = false,
    withBox3Green: Boolean = false,
    withBox4Blue: Boolean = false,
    report: (Pair<String, Any>) -> Unit,
) {
    UAlign(USTART, USTART) {
        RigidContainer(type, size, report) {
            if (withBox1Cyan) UAlign(USTART, UEND) { ExamBasicBox("cyan box", Color.Cyan, 160.dp.square, report = report) }
            if (withBox2Red) UAlign(UCENTER, UCENTER) { ExamBasicBox("red box", Color.Red, 80.dp.square, sizeRequired = true, report = report) }
            if (withBox3Green) UAlign(UEND, UEND) { ExamBasicBox("green box", Color.Green, 60.dp.square, report = report) }
            if (withBox4Blue) UAlign(USTRETCH, USTRETCH) { ExamBasicBox("blue box", Color.Blue, 30.dp.square, report = report) }
        }
    }
}

@Composable fun RigidContainer(
    type: UContainerType = UBOX,
    size: DpSize = 400.dp.square,
    report: (Pair<String, Any>) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val m = Modifier
        .background(Color.LightGray)
        .border(4.dp, Color.Blue)
        .padding(4.dp)
        .requiredSize(size)
        .reportMeasuringAndPlacement("rigid container", report)
    UContainerJvm(type, m, content)
}

val Dp.square get() = DpSize(this, this)
val Int.square get() = IntSize(this, this)

@Composable fun ExamBasicBox(
    tag: String,
    color: Color = Color.Gray,
    size: DpSize = 100.dp.square,
    sizeRequired: Boolean = false,
    report: (Pair<String, Any>) -> Unit = { println("${it.first}: ${it.second.str}") },
) {
    val m = Modifier
        .reportMeasuringAndPlacement("$tag outer", report)
        .background(color.copy(alpha = color.alpha * .8f))
        .run { if (sizeRequired) requiredSize(size) else size(size) }
        .reportMeasuringAndPlacement("$tag inner", report)
    UContainerJvm(UBOX, m)
}


@Composable fun ReportsUi(modifier: Modifier = Modifier, reports: List<Pair<String, Any>>) {
    CompositionLocalProvider(LocalDensity provides Density(1f)) {
        Column(modifier) {
            reports.forEachIndexed { idx, (key, data) ->
                Row(Modifier
                    .background(Color.White.darken(.1f * (idx % 3)))
                    .padding(2.dp)) {
                    Box(Modifier.width(400.dp)) { Text(key) }
                    Box(Modifier.weight(1f)) { Text(data.str) }
                }
            }
        }
    }
}

data class PlaceableData(val width: Int, val height: Int, val measuredWidth: Int = width, val measuredHeight: Int = height)

val Placeable.data get() = PlaceableData(width, height, measuredWidth, measuredHeight)

fun IntSize.toPlaceableData() = PlaceableData(width, height)

data class LayoutCoordinatesData(
    val size: IntSize,
    val parentLayoutCoordinatesData: LayoutCoordinatesData?,
    val parentCoordinatesData: LayoutCoordinatesData?,
    val isAttached: Boolean,

    // computed when creating data class
    val positionInWindow: Offset,
    val positionInRoot: Offset,
    val positionInParent: Offset,
    val boundsInWindow: Rect,
    val boundsInRoot: Rect,
    val boundsInParent: Rect,
)

val LayoutCoordinates.data: LayoutCoordinatesData get() = LayoutCoordinatesData(size = size,
    parentLayoutCoordinatesData = parentLayoutCoordinates?.data,
    parentCoordinatesData = parentCoordinates?.data,
    isAttached = isAttached,
    positionInWindow = positionInWindow(),
    positionInRoot = positionInRoot(),
    positionInParent = positionInParent(),
    boundsInWindow = boundsInWindow(),
    boundsInRoot = boundsInRoot(),
    boundsInParent = boundsInParent(),
)

fun Modifier.reportMeasuring(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = layout { measurable, constraints ->
    report("$tag measure with" to constraints)
    val placeable = measurable.measure(constraints)
    report("$tag measured" to placeable.data)
    layout(placeable.width, placeable.height) { placeable.place(0, 0) }
}


fun Modifier.reportPlacement(tag: String, report: (Pair<String, Any>) -> Unit): Modifier = onPlaced {
    report("$tag placed" to it.data)
}

fun Modifier.reportMeasuringAndPlacement(tag: String, report: (Pair<String, Any>) -> Unit): Modifier =
    reportMeasuring(tag, report).reportPlacement(tag, report)

fun Size.roundToIntSize() = IntSize(width.roundToInt(), height.roundToInt())
fun IntSize.toFixedConstraints() = Constraints(width, width, height, height)
fun IntSize.toMaxConstraints() = Constraints(0, width, 0, height)
fun Size.roundToFixedConstraints() = roundToIntSize().toFixedConstraints()

val Any?.str: String
    get() = when (this) {
        is Offset -> "($x,$y)"
        is Rect -> "rect: ${topLeft.str} - ${bottomRight.str}"
        is Placeable -> "placeable: $width x $height, measured = $measuredWidth x $measuredHeight"
        is PlaceableData -> "placeable: $width x $height, measured = $measuredWidth x $measuredHeight"
        is LayoutCoordinatesData -> "coordinates: size = $size, bounds in parent = ${boundsInParent.str}, attached = ${isAttached.markIfTrue()}, parent layout = ${parentLayoutCoordinatesData.markIfNull()}, parent = ${parentCoordinatesData.markIfNull()}"
        is Constraints -> "constraints: min = $minWidth x $minHeight, max = $maxWidth x $maxHeight"
        else -> toString()
    }
private fun Any?.markIfNull(markNotNull: String = "T", markNull: String = "F"): String = if (this != null) markNotNull else markNull
private fun Boolean.markIfTrue(markTrue: String = "T", markFalse: String = "F"): String = if (this) markTrue else markFalse
