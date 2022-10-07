package pl.mareklangiewicz.playgrounds

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.*
import pl.mareklangiewicz.udemo.*
import pl.mareklangiewicz.uwidgets.UBinType.*


@Preview @Composable fun MyUBoxPreview() = MyExaminedLayoutPlayground(UBOX)
@Preview @Composable fun MyURowPreview() = MyExaminedLayoutPlayground(UROW)
@Preview @Composable fun MyUColumnPreview() = MyExaminedLayoutPlayground(UCOLUMN)
@Preview @Composable fun MyAnimatedContentPreview() = MyAnimatedContentPlayground()


