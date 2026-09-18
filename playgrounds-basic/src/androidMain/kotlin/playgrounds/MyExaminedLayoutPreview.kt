package pl.mareklangiewicz.playgrounds

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.*
import pl.mareklangiewicz.udemo.*
import pl.mareklangiewicz.uwidgets.UBinType.*
import pl.mareklangiewicz.uwidgets.UWidgetsSki


@Preview @Composable fun MyUBoxPreview() = UWidgetsSki { MyExaminedLayoutPlayground(UBOX) }
@Preview @Composable fun MyURowPreview() = UWidgetsSki { MyExaminedLayoutPlayground(UROW) }
@Preview @Composable fun MyUColumnPreview() = UWidgetsSki { MyExaminedLayoutPlayground(UCOLUMN) }
@Preview @Composable fun MyAnimatedContentPreview() = UWidgetsSki {  MyAnimatedContentPlayground() }
