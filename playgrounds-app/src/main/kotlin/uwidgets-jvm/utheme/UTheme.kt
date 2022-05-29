package pl.mareklangiewicz.utheme

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

object UTheme {

    val ucolors: UColors
        @Composable
        @ReadOnlyComposable
        get() = LocalUColors.current

    val usizes: USizes
        @Composable
        @ReadOnlyComposable
        get() = LocalUSizes.current

    val ualignments: UAlignments
        @Composable
        @ReadOnlyComposable
        get() = LocalUAlignments.current
}

@Stable
class UColors(uboxBackground: Color = Color.LightGray) {
    var uboxBackground by mutableStateOf(uboxBackground)
}

@Stable
class USizes(uboxPadding: Dp = 2.dp) {
    var uboxPadding by mutableStateOf(uboxPadding)
}

@Stable
class UAlignments(uboxPadding: Dp = 2.dp) {
    var uboxPadding by mutableStateOf(uboxPadding)
}


internal val LocalUColors = staticCompositionLocalOf { UColors() }

internal val LocalUSizes = staticCompositionLocalOf { USizes() }

internal val LocalUAlignments = staticCompositionLocalOf { UAlignments() }
