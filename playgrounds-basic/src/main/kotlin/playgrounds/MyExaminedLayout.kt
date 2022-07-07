package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.Pair


@Preview @Composable fun MyUBoxPreview() = MyExaminedLayoutPlayground(UBOX)
@Preview @Composable fun MyURowPreview() = MyExaminedLayoutPlayground(UROW)
@Preview @Composable fun MyUColumnPreview() = MyExaminedLayoutPlayground(UCOLUMN)
@Preview @Composable fun MyAnimatedContentPreview() = MyAnimatedContentPlayground()

@Composable fun MyExaminedLayoutPlayground(type: UContainerType = UBOX) {

    val reportsModel = rememberReportsModel()

    Column(Modifier.fillMaxWidth()) {
        MyExaminedLayout(
            type = type,
            withSon1Cyan = true,
            withSon2Red = true,
            withSon3Green = true,
            withSon4Blue = false,
            report = reportsModel::report,
        )
        ReportsUi(Modifier.height(400.dp), reportsModel)
    }
}

@Composable fun MyExaminedLayout(
    type: UContainerType = UBOX,
    size: DpSize = 400.dp.square,
    withSon1Cyan: Boolean = false,
    withSon2Red: Boolean = false,
    withSon3Green: Boolean = false,
    withSon4Blue: Boolean = false,
    report: (Pair<String, Any>) -> Unit,
) {
    UAlign(USTART, USTART) {
        RigidFather(type, size, report) {
            if (withSon1Cyan) UAlign(USTART, UEND) { ColoredSon("cyan son", Color.Cyan, 160.dp.square, report = report) }
            if (withSon2Red) UAlign(UCENTER, UCENTER) { ColoredSon("red son", Color.Red, 80.dp.square, sizeRequired = true, report = report) }
            if (withSon3Green) UAlign(UEND, UEND) { ColoredSon("green son", Color.Green, 60.dp.square, report = report) }
            if (withSon4Blue) UAlign(USTRETCH, USTRETCH) { ColoredSon("blue son", Color.Blue, 30.dp.square, report = report) }
        }
    }
}

// sets up rigid/required/fixed constraints for children, so it's easier to reason about content
@Composable fun RigidFather(
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
        .reportMeasuringAndPlacement("rigid father", report)
    UContainerJvm(type, m, content)
}

@Composable fun ColoredSon(
    tag: String,
    color: Color = Color.Gray,
    size: DpSize = 100.dp.square,
    sizeRequired: Boolean = false,
    report: (Pair<String, Any>) -> Unit = { println("${it.first}: ${it.second.str}") },
) {
    val m = Modifier
        .reportMeasuringAndPlacement("$tag outer", report)
        .background(color.copy(alpha = color.alpha * .8f), RoundedCornerShape(4.dp))
        .run { if (sizeRequired) requiredSize(size) else size(size) }
        .reportMeasuringAndPlacement("$tag inner", report)
    UContainerJvm(UBOX, m)
}


@OptIn(ExperimentalAnimationApi::class)
@Composable fun MyAnimatedContentPlayground() {
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
    ) { MyExaminedLayoutPlayground(it) }
}

