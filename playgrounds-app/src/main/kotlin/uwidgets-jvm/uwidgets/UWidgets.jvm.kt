@file:Suppress("FunctionName")

package pl.mareklangiewicz.uwidgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*
import pl.mareklangiewicz.uwidgets.UContainerType.*

@Composable /*actual*/ fun ULessBasicBox(
    size: DpSize?,
    backgroundColor: Color,
    borderColor: Color,
    borderWidth: Dp,
    padding: Dp,
    onClick: (() -> Unit)?,
    content: @Composable () -> Unit,
) = UContainerJvm(
    type = UBOX,
    modifier = Modifier
        .thenIfNotNull(onClick) { clickable { it() } }
        .thenIfNotNull(size) { size(it) }
        .background(backgroundColor)
        .border(borderWidth, borderColor)
        .padding(borderWidth + padding),
    content = content
)

private inline fun Modifier.thenIf(condition: Boolean, add: Modifier.() -> Modifier): Modifier =
    if (condition) then(add()) else this

private inline fun <V: Any> Modifier.thenIfNotNull(value: V?, add: Modifier.(V) -> Modifier): Modifier =
    thenIf(value != null) { add(value!!) }

private fun Modifier.fillMaxIfUStretch(horizontal: UAlignmentType, vertical: UAlignmentType, fraction: Float = 1f): Modifier =
    when (horizontal) {
        USTRETCH -> when (vertical) {USTRETCH -> fillMaxSize(fraction); else -> fillMaxWidth(fraction) }
        else -> when (vertical) { USTRETCH -> fillMaxHeight(fraction); else -> this }
    }

@Composable fun UContainerJvm(type: UContainerType, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val horizontal = UTheme.alignments.horizontal
    val vertical = UTheme.alignments.vertical
    val m = modifier
        // .width(Min)
        // .fillMaxIfUStretch(horizontal, vertical)
            // width(Min) is an experiment (probably wrong for my alignment requirements) , but it throws anyway:
            // IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is not supported
    when (type) {
        UBOX -> Box(m, Alignment.of(horizontal, vertical)) { content() }
        UROW -> Row(m, Arrangement.ofHorizontal(horizontal), Alignment.ofVertical(vertical)) { content() }
        UCOLUMN -> Column(m, Arrangement.ofVertical(vertical), Alignment.ofHorizontal(horizontal)) { content() }
    }
}

private fun Alignment.Companion.of(horizontal: UAlignmentType, vertical: UAlignmentType): Alignment =
    when(horizontal) {
        USTART -> when(vertical) { USTART -> TopStart; UEND -> BottomStart; else -> CenterStart }
        UEND -> when(vertical) { USTART -> TopEnd; UEND -> BottomEnd; else -> CenterEnd }
        else -> when(vertical) { USTART -> TopCenter; UEND -> BottomCenter; else -> Center }
    }

private fun Arrangement.ofHorizontal(horizontal: UAlignmentType): Arrangement.Horizontal = when(horizontal) {
    USTART -> Start
    UEND -> End
    else -> SpaceEvenly
}

private fun Arrangement.ofVertical(vertical: UAlignmentType): Arrangement.Vertical = when(vertical) {
    USTART -> Top
    UEND -> Bottom
    else -> SpaceEvenly
}

private fun Alignment.Companion.ofHorizontal(horizontal: UAlignmentType): Alignment.Horizontal = when(horizontal) {
    USTART -> Start
    UEND -> End
    else -> CenterHorizontally
}

private fun Alignment.Companion.ofVertical(vertical: UAlignmentType): Alignment.Vertical = when(vertical) {
    USTART -> Top
    UEND -> Bottom
    else -> CenterVertically
}

@Composable /*actual*/ fun UBasicBox(content: @Composable () -> Unit) = UContainerJvm(UBOX, content = content)
@Composable /*actual*/ fun UBasicColumn(content: @Composable () -> Unit) = UContainerJvm(UCOLUMN, content = content)
@Composable /*actual*/ fun UBasicRow(content: @Composable () -> Unit) = UContainerJvm(UROW, content = content)

@Composable /*actual*/ fun UText(text: String, bold: Boolean = false, mono: Boolean = false) {
    val style = LocalTextStyle.current.copy(
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        fontFamily = if (mono) FontFamily.Monospace else FontFamily.Default
    )
    UContainerJvm(UBOX) { Text(text, maxLines = 1, style = style) }
}

@Composable /*actual*/ fun UBasicText(text: String) = Text(text, maxLines = 1)

@Composable /*actual*/ fun UTabs(vararg tabs: String, onSelected: (index: Int, tab: String) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title, style = MaterialTheme.typography.titleSmall) },
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index; onSelected(index, title) }
            )
        }
    }
}

