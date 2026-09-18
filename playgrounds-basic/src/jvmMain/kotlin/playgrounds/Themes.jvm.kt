package pl.mareklangiewicz.playgrounds

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

/** Desktop has no wallpaper-derived accent to read, so the static schemes always win. */
@Composable actual fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme? = null
