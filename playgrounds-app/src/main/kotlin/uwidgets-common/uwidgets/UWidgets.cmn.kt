@file:Suppress("FunctionName")

package pl.mareklangiewicz.uwidgets

import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*

fun Color.lighten(fraction: Float = 0.1f) = lerp(this, Color.White, fraction)
fun Color.darken(fraction: Float = 0.1f) = lerp(this, Color.Black, fraction)

@Composable fun UBox(depthIncrease: Int = 1, content: @Composable () -> Unit) {
    val depth = ULocalDepth.current
    val bg = ULocalBackground.current.forDepth(depth)
    ULessBasicBox(
        backgroundColor = bg,
        borderColor = bg.darken(.1f),
        borderWidth = 1.dp,
        padding = 2.dp,
    ) {
        CompositionLocalProvider(ULocalDepth provides depth + depthIncrease, content = content)
    }
}

@Composable fun UColumn(depthIncrease: Int = 1, content: @Composable () -> Unit) {
    UBox(depthIncrease) { UBasicColumn(content) }
}

@Composable fun URow(depthIncrease: Int = 1, content: @Composable () -> Unit) {
    UBox(depthIncrease) { UBasicRow(content) }
}

/*
@Composable expect fun ULessBasicBox(
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    padding: Dp = 0.dp,
    content: @Composable () -> Unit,
)

*/
/*
@Composable expect fun UBasicBox(content: @Composable () -> Unit)

*/
/*
@Composable expect fun UBasicColumn(content: @Composable () -> Unit)

*/
/*
@Composable expect fun UBasicRow(content: @Composable () -> Unit)

*/
@Composable fun UBoxedText(
    text: String,
    center: Boolean = false,
    bold: Boolean = false,
    mono: Boolean = false,
    depthIncrease: Int = 1,
) = UBox(depthIncrease) { UText(text, center, bold, mono) }

/*
@Composable expect fun UText(
    text: String,
    center: Boolean = false,
    bold: Boolean = false,
    mono: Boolean = false,
)

*/
/*
@Composable expect fun UBasicText(text: String)

*/
val ULocalDepth = compositionLocalOf { 0 }

val ULocalBackground = compositionLocalOf { Color.LightGray }

@Composable private fun Color.forDepth(depth: Int) = lighten((depth % 3 + 1) * 0.25f)


/*
@Composable expect fun UTabs(vararg tabs: String, onSelected: (idx: Int, tab: String) -> Unit)

*/
@Composable fun UTabs(vararg contents: Pair<String, @Composable () -> Unit>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    UColumn {
        UTabs(*contents.map { it.first }.toTypedArray()) { idx, tab ->
            selectedTabIndex = idx
        }
        contents[selectedTabIndex].second()
    }
}

@Composable
internal fun UTabsCmn(vararg tabs: String, onSelected: (idx: Int, tab: String) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    URow {
        tabs.forEachIndexed { index, title ->
            UOnBoxClick({ selectedTabIndex = index; onSelected(index, title) }) {
                UBoxedText(title, center = true, bold = index == selectedTabIndex, mono = true)
            }
        }
    }
}

@Composable
fun UOnBoxClick(onBoxClick: () -> Unit, content: @Composable () -> Unit) {
    CompositionLocalProvider(ULocalOnBoxClick provides onBoxClick, content = content)
}

// It's a really hacky solution for multiplatform minimalist onClick support.
// Mostly to avoid more parameters in functions. Probably will be changed later.
val ULocalOnBoxClick = compositionLocalOf<(() -> Unit)?> { null }