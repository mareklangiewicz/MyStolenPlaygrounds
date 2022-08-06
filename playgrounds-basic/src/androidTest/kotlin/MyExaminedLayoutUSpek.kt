package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.test.junit4.*
import androidx.compose.ui.unit.*
import org.junit.*
import org.junit.runner.*
import pl.mareklangiewicz.umath.*
import pl.mareklangiewicz.uspek.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.math.*

@RunWith(USpekJUnit4Runner::class)
class MyExaminedLayoutUSpek {
    init {
        uspekLog = {
            if (it.failed) Log.e("uspek", it.status)
            else Log.w("uspek", it.status)
        }
    }

    @get:Rule val rule = createComposeRule()

    @USpekTestTree(23) fun layout() = rule.layout()
}

private val reportsModel = ReportsModel()

fun ComposeContentTestRule.layout() = with(density) {

    val reports = reportsModel.reports
    val report = reportsModel::report

    reports.clear()

    "On MyExaminedLayout" o {
        var type by mutableStateOf(UBOX)
        var rigidSizeDp by mutableStateOf(400.dp.square)
        val rigidSizePx = rigidSizeDp.toSize().copyRoundToIntSize()
        var withSon1Cyan by mutableStateOf(false)
        var withSon2Red by mutableStateOf(false)
        var withSon3Green by mutableStateOf(false)
        var withSon4Blue by mutableStateOf(false)
        setContent {
            MyExaminedLayout(
                type = type,
                size = rigidSizeDp,
                withSon1Cyan = withSon1Cyan,
                withSon2Red = withSon2Red,
                withSon3Green = withSon3Green,
                withSon4Blue = withSon4Blue,
                report = report
            )
        }
        waitForIdle()

        "With rigid father type UBOX" o {

            "With no children" o {

                "only root rigid father is measured and placed" o { reports.size eq 3 }

                "rigid father gets measured with fixed constraints" o {
                    reports[0] eq ("rigid father measure with" to rigidSizePx.copyToAllConstraints())
                    reports[1] eq ("rigid father measured" to rigidSizePx.copyToPlaceableData())
                }
                "rigid father is placed and attached" o {
                    reports[2].reportedPlacement("rigid father") { size == rigidSizePx && isAttached }
                }
            }

            "When cyan son gets enabled" o {
                withSon1Cyan = true
                waitForIdle()

                "rigid father starts measure again with the same constraints" o { reports[3] eq reports[0] }

                val cyanSonSizePx = 160.dp.square.toSize().copyRoundToIntSize()
                "cyan son gets measured" o {
                    reports[4] eq ("cyan son outer measure with" to rigidSizePx.copyToMaxConstraints())
                    reports[5] eq ("cyan son inner measure with" to cyanSonSizePx.copyToAllConstraints())
                    reports[6] eq ("cyan son inner measured" to cyanSonSizePx.copyToPlaceableData())
                    reports[7] eq ("cyan son outer measured" to cyanSonSizePx.copyToPlaceableData())
                }

                "rigid father gets remeasured and placed the same way as before" o {
                    reports[8] eq reports[1]
                    reports[9] eq reports[2]
                }

                "cyan son gets placed on bottom left side" o {
                    reports[10].reportedPlacement("cyan son outer") {
                        size == cyanSonSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                    reports[11].reportedPlacement("cyan son inner") {
                        size == cyanSonSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                }

                "rigid father starts measure again with the same constraints" o { reports[12] eq reports[0] }

                "When blue son stretched both ways gets enabled" o {
                    withSon4Blue = true
                    waitForIdle()

                    "blue son gets measured" o {
                        reports[13] eq ("blue son outer measure with" to rigidSizePx.copyToAllConstraints())
                        reports[14] eq ("blue son inner measure with" to rigidSizePx.copyToAllConstraints())
                        reports[15] eq ("blue son inner measured" to rigidSizePx.copyToPlaceableData())
                        reports[16] eq ("blue son outer measured" to rigidSizePx.copyToPlaceableData())
                    }
                    "rigid father gets remeasured and placed the same way" o {
                        reports[17] eq reports[1]
                        reports[18] eq reports[2]
                    }
                    "cyan son gets placed again the same way" o {
                        reports[19] eq reports[10]
                        reports[20] eq reports[11]
                    }
                    "blue son gets placed with fixed rigid father size" o {
                        reports[21].reportedPlacement("blue son outer") { size == rigidSizePx && positionInParent == Offset.Zero }
                        reports[22].reportedPlacement("blue son inner") { size == rigidSizePx && positionInParent == Offset.Zero }
                    }
                    "no other reports" o { reports.size eq 23 }
                }

                "When green son stretched horizontally gets enabled" o {
                    withSon3Green = true
                    waitForIdle()

                    val greenSonSizePx = 60.dp.square.toSize().copyRoundToIntSize()
                    "green son gets measured" o {
                        reports[13] eq ("green son outer measure with" to rigidSizePx.copyToAllConstraints(minH = 0))
                        reports[14] eq ("green son inner measure with" to rigidSizePx.copyToAllConstraints(minH = greenSonSizePx.height, maxH = greenSonSizePx.height))
                        reports[15] eq ("green son inner measured" to rigidSizePx.copyToPlaceableData(h = greenSonSizePx.height))
                        reports[16] eq ("green son outer measured" to rigidSizePx.copyToPlaceableData(h = greenSonSizePx.height))
                    }
                    "rigid father gets remeasured and placed the same way" o {
                        reports[17] eq reports[1]
                        reports[18] eq reports[2]
                    }
                    "cyan son gets placed again the same way" o {
                        reports[19] eq reports[10]
                        reports[20] eq reports[11]
                    }
                    "green son gets placed stretched horizontally" o {
                        reports[21].reportedPlacement("green son outer") {
                            size.width == rigidSizePx.width
                                && size.height == greenSonSizePx.height
                                && boundsInParent.left == 0f
                                && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                        }
                        reports[22].reportedPlacement("green son inner") {
                            size.width == rigidSizePx.width
                                && size.height == greenSonSizePx.height
                                && boundsInParent.left == 0f
                                && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                        }
                    }
                    "no other reports" o { reports.size eq 23 }
                }
                // TODO NOW: other types UROW UCOLUMN
            }
        }
    }
}
