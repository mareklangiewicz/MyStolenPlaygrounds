package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.test.junit4.*
import androidx.compose.ui.unit.*
import org.junit.*
import org.junit.runner.*
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

    @USpekTestTree(17) fun layout() = rule.layout()
}

private val reportsModel = ReportsModel()

fun ComposeContentTestRule.layout() = with(density) {

    val reports = reportsModel.reports
    val report = reportsModel::report

    reports.clear()

    "On MyExaminedLayout" o {
        var type by mutableStateOf(UBOX)
        var rigidSizeDp by mutableStateOf(400.dp.square)
        val rigidSizePx = rigidSizeDp.toSize().roundToIntSize()
        var withBox1Cyan by mutableStateOf(false)
        var withBox2Red by mutableStateOf(false)
        var withBox3Green by mutableStateOf(false)
        var withBox4Blue by mutableStateOf(false)
        setContent {
            MyExaminedLayout(
                type = type,
                size = rigidSizeDp,
                withSon1Cyan = withBox1Cyan,
                withSon2Red = withBox2Red,
                withSon3Green = withBox3Green,
                withSon4Blue = withBox4Blue,
                report = report
            )
        }
        waitForIdle()

        "With rigid father type UBOX" o {

            "With no children" o {

                "only root rigid father is measured and placed" o { reports.size eq 3 }

                "rigid father gets measured with fixed constraints" o {
                    reports[0] eq ("rigid father measure with" to rigidSizePx.toFixedConstraints())
                    reports[1] eq ("rigid father measured" to rigidSizePx.toPlaceableData())
                }
                "rigid father is placed and attached" o {
                    reports[2].reportedPlacement("rigid father") { size == rigidSizePx && isAttached }
                }
            }

            "When cyan son gets enabled" o {
                withBox1Cyan = true
                waitForIdle()

                "rigid father starts measure again with the same constraints" o { reports[3] eq reports[0] }

                val cyanBoxSizePx = 160.dp.square.toSize().roundToIntSize()
                "cyan son gets measured" o {
                    reports[4] eq ("cyan son outer measure with" to rigidSizePx.toMaxConstraints())
                    reports[5] eq ("cyan son inner measure with" to cyanBoxSizePx.toFixedConstraints())
                    reports[6] eq ("cyan son inner measured" to cyanBoxSizePx.toPlaceableData())
                    reports[7] eq ("cyan son outer measured" to cyanBoxSizePx.toPlaceableData())
                }

                "rigid father gets remeasured and placed the same way as before" o {
                    reports[8] eq reports[1]
                    reports[9] eq reports[2]
                }

                "cyan son gets placed on bottom left side" o {
                    reports[10].reportedPlacement("cyan son outer") {
                        size == cyanBoxSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                    reports[11].reportedPlacement("cyan son inner") {
                        size == cyanBoxSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                }

                "rigid father starts measure again with the same constraints" o { reports[12] eq reports[0] }

                "When blue son gets enabled" o {
                    withBox4Blue = true
                    waitForIdle()

                    "blue son gets measured" o {
                        reports[13] eq ("blue son outer measure with" to rigidSizePx.toFixedConstraints())
                        reports[14] eq ("blue son inner measure with" to rigidSizePx.toFixedConstraints())
                        reports[15] eq ("blue son inner measured" to rigidSizePx.toPlaceableData())
                        reports[16] eq ("blue son outer measured" to rigidSizePx.toPlaceableData())
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
            }
        }
    }
}
