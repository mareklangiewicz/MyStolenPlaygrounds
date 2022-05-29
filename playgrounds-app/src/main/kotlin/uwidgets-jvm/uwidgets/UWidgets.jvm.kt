@file:Suppress("FunctionName")

package pl.mareklangiewicz.uwidgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*

@Composable /*actual*/ fun ULessBasicBox(
    backgroundColor: Color,
    borderColor: Color,
    borderWidth: Dp,
    padding: Dp,
    content: @Composable () -> Unit,
) {
    Box(
        Modifier
            .background(backgroundColor)
            .border(borderWidth, borderColor)
            .padding(borderWidth + padding)
    ) { content() }
}

@Composable /*actual*/ fun UBasicBox(content: @Composable () -> Unit) {
    val onBoxClick = ULocalOnBoxClick.current
    val modifier = onBoxClick?.let { Modifier.clickable { it() } } ?: Modifier
    CompositionLocalProvider(ULocalOnBoxClick provides null) {
        Box(modifier) { content() }
    }
}

@Composable /*actual*/ fun UBasicColumn(content: @Composable () -> Unit) {
    val onBoxClick = ULocalOnBoxClick.current
    val modifier = onBoxClick?.let { Modifier.clickable { it() } } ?: Modifier
    CompositionLocalProvider(ULocalOnBoxClick provides null) {
        Column(modifier) { content() }
    }
}

@Composable /*actual*/ fun UBasicRow(content: @Composable () -> Unit) {
    val onBoxClick = ULocalOnBoxClick.current
    val modifier = onBoxClick?.let { Modifier.clickable { it() } } ?: Modifier
    CompositionLocalProvider(ULocalOnBoxClick provides null) {
        Row(modifier) { content() }
    }
}

@Composable /*actual*/ fun UText(text: String, center: Boolean, bold: Boolean, mono: Boolean) {
    val style = LocalTextStyle.current.copy(
        textAlign = if (center) TextAlign.Center else TextAlign.Start,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        fontFamily = if (mono) FontFamily.Monospace else FontFamily.Default
    )
    Text(text, maxLines = 1, style = style)
}

@Composable /*actual*/ fun UBasicText(text: String) = Text(text, maxLines = 1)

@Composable /*actual*/ fun UTabs(vararg tabs: String, onSelected: (index: Int, tab: String) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title, style = MaterialTheme.typography.headlineSmall) },
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index; onSelected(index, title) }
            )
        }
    }
}

