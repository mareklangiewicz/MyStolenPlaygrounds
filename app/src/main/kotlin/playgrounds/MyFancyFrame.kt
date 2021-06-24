package pl.mareklangiewicz.myfancyframe

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.playgrounds.PlaygroundsTheme


@Composable
fun MyFancyFrame(
    title: String? = null,
    highlighted: Boolean = false,
    selected: Boolean = false,
    grayed: Boolean = false,
    onTitleClick: () -> Unit = {},
    content: @Composable () -> Unit
) = MyFancySurface(highlighted, selected, grayed) {
    Column {
        if (title != null)
            Text(text = title, style = MaterialTheme.typography.caption)
        Box(Modifier.border(2.dp, LocalColorNormal.current).padding(2.dp).fillMaxSize()) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MyFancySurface(
    highlighted: Boolean = false,
    selected: Boolean = false,
    grayed: Boolean = false,
    onClick: (() -> Unit) = {},
    content: @Composable () -> Unit
) = Surface(
    onClick = onClick,
    color = MaterialTheme.colors.surface, // TODO: own "theme" for MyFancyFrame
    elevation = when {
        highlighted -> 12.dp
        selected -> 8.dp
        grayed -> 2.dp
        else -> 4.dp
    }
) {
    val color1 = when {
        highlighted -> LocalColorHighlighted.current
        grayed -> LocalColorGrayed.current
        else -> LocalColorNormal.current
    }
    val color2 = when {
        highlighted -> LocalColorHighlighted.current
        grayed -> LocalColorGrayed.current
        selected -> LocalColorSelected.current
        else -> LocalColorNormal.current
    }
    Box(
        Modifier
            .border(2.dp, color1)
            .padding(2.dp)
            .border(2.dp, color2)
            .padding(2.dp)
            .border(2.dp, color1)
            .padding(6.dp),
    ) { content() }
}

@Preview
@Composable
fun MyFancyFramePreview() {
    PlaygroundsTheme {
        CompositionLocalProvider(LocalDensity provides Density(8f)) {
            Box(Modifier.padding(20.dp)) {
                MyFancyFrame("Some box", selected = true, onTitleClick = { println("click") }) {
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

private val LocalColorHighlighted = staticCompositionLocalOf { Color(0.718f, 0.11f, 0.11f, 1.0f) }
private val LocalColorSelected = staticCompositionLocalOf { Color(0.051f, 0.278f, 0.631f, 1.0f) }
private val LocalColorNormal = staticCompositionLocalOf { Color(0.31f, 0.506f, 0.667f, 1.0f) }
private val LocalColorGrayed = staticCompositionLocalOf { Color(0.635f, 0.635f, 0.635f, 1.0f) }


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