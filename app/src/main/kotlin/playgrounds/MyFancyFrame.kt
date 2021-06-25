package pl.mareklangiewicz.myfancyframe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyFancyFrame(
    modifier: Modifier = Modifier,
    title: String? = null,
    highlighted: Boolean = false,
    selected: Boolean = false,
    grayed: Boolean = false,
    onTitleClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val col = when {
        highlighted -> LocalColorHighlighted.current
        grayed -> LocalColorGrayed.current
        selected -> LocalColorSelected.current
        else -> LocalColorNormal.current
    }
    Box(modifier.padding(6.dp)) {
        Surface(onClick = onTitleClick, shape = CutCornerShape(topStart = 8.dp, bottomEnd = 2.dp), color = col, elevation = 8.dp) {
            Column {
                if (title != null)
                    Text(
                        text = title,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                        maxLines = 1,
                        style = MaterialTheme.typography.caption
                    )
                MyFancySurface {
                    content()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MyFancySurface(content: @Composable () -> Unit) = Surface(
    Modifier.padding(1.dp).fillMaxSize(),
    shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
    color = MaterialTheme.colors.surface, // TODO: own "theme" for MyFancyFrame
    elevation = 1.dp
) {
    Box(Modifier.fillMaxSize().padding(8.dp)) { content() }
}

@Preview
@Composable
fun MyFancyFramePreview() {
    PlaygroundsTheme(darkTheme = false) {
        CompositionLocalProvider(LocalDensity provides Density(4f)) {
            Box(Modifier.padding(20.dp)) {
                MyFancyFrame(title = "Some box", grayed = false, onTitleClick = { println("click") }) {
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