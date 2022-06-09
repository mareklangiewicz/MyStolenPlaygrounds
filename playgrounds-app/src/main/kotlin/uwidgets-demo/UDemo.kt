package pl.mareklangiewicz.udemo

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import pl.mareklangiewicz.utheme.*
import pl.mareklangiewicz.uwidgets.*
import pl.mareklangiewicz.uwidgets.UAlignmentType.*

@Composable
fun UDemo() = UAlign(USTRETCH, USTRETCH) {
    UTabs(
        "UDemo 0" to { UDemo0() },
        "UDemo 1" to { UText("TODO UDemo1") },
        "UDemo 2" to { UText("TODO UDemo2") },
    )
}

@Composable
fun UDemo0() = URow {
    UColumn(DpSize(30.dp, 800.dp)) { UDemoTexts(5, growFactor = 0) }
    UColumn {
        var switch1 by remember { mutableStateOf(USTART) }
        var switch2 by remember { mutableStateOf(USTART) }
        var switch3 by remember { mutableStateOf(USTART) }
        var switch4 by remember { mutableStateOf(USTART) }
        UColumn {
            UText("Align switchers:")
            val options = UAlignmentType.values().map { it.css }.toTypedArray()
            UTabs(*options) { idx, tab -> switch1 = UAlignmentType.css(tab) }
            UTabs(*options) { idx, tab -> switch2 = UAlignmentType.css(tab) }
            UTabs(*options) { idx, tab -> switch3 = UAlignmentType.css(tab) }
            UTabs(*options) { idx, tab -> switch4 = UAlignmentType.css(tab) }
        }
        UAlign(horizontal = switch1, vertical = switch2) {
            UBox { UBox { URow { UDemoTexts(3) } } }
            UBox { UBox { URow { UDemoTexts(15) } } }
            UTheme(UColors(uboxBackground = Color.hsl(200f, .4f, .8f, .6f))) {
                UBox { UBox { UBox { UDemoTexts() } } }
            }
            UAlign(horizontal = switch3, vertical = switch4) {
                UBox { UBox { UColumn { UDemoTexts(10, growFactor = 3) } } }
            }
        }
    }
}

@Composable
private fun UDemoTexts(
    count: Int = 20,
    boxed: Boolean = true,
    center: Boolean = true,
    bold: Boolean = true,
    mono: Boolean = true,
    growFactor: Int = 1,
) {
    require(boxed || !center)
    repeat(count) { i ->
        val c = 'A' + i
        val s = "$c".repeat(1 + i * growFactor)
        if (boxed) UBoxedText(s, center, bold, mono)
        else UText(s, bold, mono)
    }
}