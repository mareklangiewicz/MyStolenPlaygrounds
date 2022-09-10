package pl.mareklangiewicz.playgrounds

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.*
import pl.mareklangiewicz.udemo.*
import pl.mareklangiewicz.uwidgets.*


@Preview @Composable fun MyUBoxPreview() = MyExaminedLayoutPlayground(UContainerType.UBOX)
@Preview @Composable fun MyURowPreview() = MyExaminedLayoutPlayground(UContainerType.UROW)
@Preview @Composable fun MyUColumnPreview() = MyExaminedLayoutPlayground(UContainerType.UCOLUMN)
@Preview @Composable fun MyAnimatedContentPreview() = MyAnimatedContentPlayground()


