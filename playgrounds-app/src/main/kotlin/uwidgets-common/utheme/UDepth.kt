package pl.mareklangiewicz.utheme

import androidx.compose.runtime.*


object UDepth {

    val depth: Int
        @Composable
        @ReadOnlyComposable
        get() = LocalUDepth.current

    val settings: UDepthSettings
        @Composable
        @ReadOnlyComposable
        get() = LocalUDepthSettings.current

    val appearance: Float
        @Composable
        @ReadOnlyComposable
        get() = ((depth % settings.modulo + 1) * settings.steepness).coerceIn(0f, 1f)

}

@Composable fun UDepth(depth: Int = UDepth.depth + UDepth.settings.step, settings: UDepthSettings? = null, content: @Composable () -> Unit) =
    if (settings == null) CompositionLocalProvider(LocalUDepth provides depth, content = content)
    else CompositionLocalProvider(LocalUDepth provides depth, LocalUDepthSettings provides settings, content = content)

private val LocalUDepth = staticCompositionLocalOf { 0 }
private val LocalUDepthSettings = staticCompositionLocalOf { UDepthSettings() }

class UDepthSettings(step: Int = 1, modulo: Int = 4, steepness: Float = .2f) {
    /** how much local depth increases every time */
    var step by mutableStateOf(step)
    /** after how big depth, more depth appearance (like background) is wrapped around - the depth itself is not wrapped */
    var modulo by mutableStateOf(modulo)
    /** how much appearance (like background) is changed depending on depth */
    var steepness by mutableStateOf(steepness)
}

