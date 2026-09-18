package pl.mareklangiewicz.playgrounds

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable actual fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme? =
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) null
  else LocalContext.current.let { if (darkTheme) dynamicDarkColorScheme(it) else dynamicLightColorScheme(it) }
