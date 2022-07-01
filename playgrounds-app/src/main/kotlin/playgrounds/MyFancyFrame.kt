package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFancyFrame(
    modifier: Modifier = Modifier,
    title: String? = null,
    highlighted: Boolean = false,
    selected: Boolean = false,
    grayed: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val col = when {
        highlighted -> LocalColorHighlighted.current
        grayed -> LocalColorGrayed.current
        selected -> LocalColorSelected.current
        else -> LocalColorNormal.current
    }
    Box(modifier.padding(6.dp)) {
        Surface(onClick = onClick, shape = CutCornerShape(topStart = 8.dp, bottomEnd = 2.dp), color = col, shadowElevation = 8.dp) {
            Column {
                if (title != null)
                    Text(
                        text = title,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 2.dp).wrapContentWidth(End, unbounded = true),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                MyFancySurface {
                    content()
                }
            }
        }
    }
}

@Composable
private fun MyFancySurface(content: @Composable () -> Unit) = Surface(
    modifier = Modifier.padding(1.dp).fillMaxSize(),
    shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
    color = MaterialTheme.colorScheme.surface, // TODO: own "theme" for MyFancyFrame
    shadowElevation = 1.dp
) {
    Box(Modifier.fillMaxSize().padding(8.dp)) { content() }
}

@Preview
@Composable
fun MyFancyFramePreview() {
    PlaygroundsTheme(darkTheme = false) {
        CompositionLocalProvider(LocalDensity provides Density(4f)) {
            Box(Modifier.padding(20.dp)) {
                MyFancyFrame(title = "Some box", grayed = false, onClick = { println("click") }) {
                    Box(
                        Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ) {
                        Text("Some content", Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

private val LocalColorHighlighted = staticCompositionLocalOf { Color(0.937f, 0.604f, 0.604f, 1.0f) }
private val LocalColorSelected = staticCompositionLocalOf { Color(0.255f, 0.498f, 0.875f, 1.0f) }
private val LocalColorNormal = staticCompositionLocalOf { Color(0.643f, 0.769f, 0.875f, 1.0f) }
private val LocalColorGrayed = staticCompositionLocalOf { Color(0.812f, 0.812f, 0.812f, 1.0f) }


@Composable fun MyFancyFrameTheme(
    colorHighlighted: Color = LocalColorHighlighted.current,
    colorSelected: Color = LocalColorSelected.current,
    colorNormal: Color = LocalColorNormal.current,
    colorGrayed: Color = LocalColorGrayed.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorHighlighted provides colorHighlighted,
        LocalColorSelected provides colorSelected,
        LocalColorNormal provides colorNormal,
        LocalColorGrayed provides colorGrayed,
        content = content
    )
}