@file:Suppress("FunctionName")

package pl.mareklangiewicz.uwidgets

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*

enum class UContainerType { UBOX, UROW, UCOLUMN }

enum class UAlignmentType(val css: String) {
    USTART("start"), UEND("end"), UCENTER("center"), USTRETCH("stretch");
    companion object {
        fun css(css: String) = UAlignmentType.values().first { it.css == css }
    }
}

fun Color.lighten(fraction: Float = 0.1f) = lerp(this, Color.White, fraction.coerceIn(0f, 1f))
fun Color.darken(fraction: Float = 0.1f) = lerp(this, Color.Black, fraction.coerceIn(0f, 1f))

@Composable fun UBox(size: DpSize? = null, content: @Composable () -> Unit) = ULessBasicBox(
    size = size,
    backgroundColor = UTheme.colors.uboxBackground,
    borderColor = UTheme.colors.uboxBorder,
    borderWidth = UTheme.sizes.uboxBorder,
    padding = UTheme.sizes.uboxPadding,
    onClick = LocalUOnBoxClick.current
) { UDepth { CompositionLocalProvider(LocalUOnBoxClick provides null, content = content) } }

// It's a really hacky solution for multiplatform minimalist onClick support.
// Mostly to avoid more parameters in functions. Probably will be changed later.
@Composable fun UOnBoxClick(onBoxClick: () -> Unit, content: @Composable () -> Unit) =
    CompositionLocalProvider(LocalUOnBoxClick provides onBoxClick, content = content)

private val LocalUOnBoxClick = staticCompositionLocalOf<(() -> Unit)?> { null }


@Composable fun UColumn(size: DpSize? = null, content: @Composable () -> Unit) = UBox(size) { UBasicColumn(content) }

@Composable fun URow(size: DpSize? = null, content: @Composable () -> Unit) = UBox(size) { UBasicRow(content) }

/*
@Composable expect fun ULessBasicBox(
    size: DpSize? = null,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    padding: Dp = 0.dp,
    onClick: (() -> Unit)? = null,
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
@Composable fun UBoxedText(text: String, center: Boolean = false, bold: Boolean = false, mono: Boolean = false) =
    UBox {
        UAlign(
            if (center) UCENTER else UTheme.alignments.horizontal,
            if (center) UCENTER else UTheme.alignments.vertical,
        ) { UText(text, bold, mono) }
    }

/*
@Composable expect fun UText(text: String, bold: Boolean = false, mono: Boolean = false)

*/
/*
@Composable expect fun UBasicText(text: String)


*/
/*
@Composable expect fun UTabs(vararg tabs: String, onSelected: (idx: Int, tab: String) -> Unit)

*/
@Composable fun UTabs(vararg contents: Pair<String, @Composable () -> Unit>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    UColumn {
        UTabs(*contents.map { it.first }.toTypedArray()) { idx, _ -> selectedTabIndex = idx }
        contents[selectedTabIndex].second()
    }
}

@Composable
internal fun UTabsCmn(vararg tabs: String, onSelected: (idx: Int, tab: String) -> Unit) = UAlign(USTART, USTART) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    URow {
        tabs.forEachIndexed { index, title ->
            UOnBoxClick({ selectedTabIndex = index; onSelected(index, title) }) {
                UBoxedText(title, center = true, bold = index == selectedTabIndex, mono = true)
            }
        }
    }
}

