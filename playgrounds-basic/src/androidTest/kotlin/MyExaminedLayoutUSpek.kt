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
import pl.mareklangiewicz.uwidgets.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.math.*


// TODO: Move it all to udemo in UWidgets repo

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

private val ureports = UReports { Log.i("rspek", it.ustr) }

fun ComposeContentTestRule.layout() = with(density) {

    val history = ureports.history

    history.clear()

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
                onUReport = ureports::invoke
            )
        }
        waitForIdle()

        "With rigid father type UBOX" o {

            "With no children" o {

                "only root rigid father is measured and placed" o { history.size eq 3 }

                "rigid father gets measured with fixed constraints" o {
                    history[0] eq ("rigid father measure with" to rigidSizePx.copyToAllConstraints())
                    history[1] eq ("rigid father measured" to rigidSizePx.copyToUPlaceableData())
                }
                "rigid father is placed and attached" o {
                    history[2].reportedPlacement("rigid father") { size == rigidSizePx && isAttached }
                }
            }

            "When cyan son gets enabled" o {
                withSon1Cyan = true
                waitForIdle()

                "rigid father starts measure again with the same constraints" o { history[3] eq history[0] }

                val cyanSonSizePx = 160.dp.square.toSize().copyRoundToIntSize()
                "cyan son gets measured" o {
                    history[4] eq ("cyan son outer measure with" to rigidSizePx.copyToMaxConstraints())
                    history[5] eq ("cyan son inner measure with" to cyanSonSizePx.copyToAllConstraints())
                    history[6] eq ("cyan son inner measured" to cyanSonSizePx.copyToUPlaceableData())
                    history[7] eq ("cyan son outer measured" to cyanSonSizePx.copyToUPlaceableData())
                }

                "rigid father gets remeasured and placed the same way as before" o {
                    history[8] eq history[1]
                    history[9] eq history[2]
                }

                "cyan son gets placed on bottom left side" o {
                    history[10].reportedPlacement("cyan son outer") {
                        size == cyanSonSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                    history[11].reportedPlacement("cyan son inner") {
                        size == cyanSonSizePx && boundsInParent.left == 0f && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                    }
                }

                "rigid father starts measure again with the same constraints" o { history[12] eq history[0] }

                "When blue son stretched both ways gets enabled" o {
                    withSon4Blue = true
                    waitForIdle()

                    "blue son gets measured" o {
                        history[13] eq ("blue son outer measure with" to rigidSizePx.copyToAllConstraints())
                        history[14] eq ("blue son inner measure with" to rigidSizePx.copyToAllConstraints())
                        history[15] eq ("blue son inner measured" to rigidSizePx.copyToUPlaceableData())
                        history[16] eq ("blue son outer measured" to rigidSizePx.copyToUPlaceableData())
                    }
                    "rigid father gets remeasured and placed the same way" o {
                        history[17] eq history[1]
                        history[18] eq history[2]
                    }
                    "cyan son gets placed again the same way" o {
                        history[19] eq history[10]
                        history[20] eq history[11]
                    }
                    "blue son gets placed with fixed rigid father size" o {
                        history[21].reportedPlacement("blue son outer") { size == rigidSizePx && positionInParent == Offset.Zero }
                        history[22].reportedPlacement("blue son inner") { size == rigidSizePx && positionInParent == Offset.Zero }
                    }
                    "no other reports" o { history.size eq 23 }
                }

                "When green son stretched horizontally gets enabled" o {
                    withSon3Green = true
                    waitForIdle()

                    val greenSonSizePx = 60.dp.square.toSize().copyRoundToIntSize()
                    "green son gets measured" o {
                        history[13] eq ("green son outer measure with" to rigidSizePx.copyToAllConstraints(minH = 0))
                        history[14] eq ("green son inner measure with" to rigidSizePx.copyToAllConstraints(minH = greenSonSizePx.height, maxH = greenSonSizePx.height))
                        history[15] eq ("green son inner measured" to rigidSizePx.copyToUPlaceableData(h = greenSonSizePx.height))
                        history[16] eq ("green son outer measured" to rigidSizePx.copyToUPlaceableData(h = greenSonSizePx.height))
                    }
                    "rigid father gets remeasured and placed the same way" o {
                        history[17] eq history[1]
                        history[18] eq history[2]
                    }
                    "cyan son gets placed again the same way" o {
                        history[19] eq history[10]
                        history[20] eq history[11]
                    }
                    "green son gets placed stretched horizontally" o {
                        history[21].reportedPlacement("green son outer") {
                            size.width == rigidSizePx.width
                                && size.height == greenSonSizePx.height
                                && boundsInParent.left == 0f
                                && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                        }
                        history[22].reportedPlacement("green son inner") {
                            size.width == rigidSizePx.width
                                && size.height == greenSonSizePx.height
                                && boundsInParent.left == 0f
                                && boundsInParent.bottom.roundToInt() == rigidSizePx.height
                        }
                    }
                    "no other reports" o { history.size eq 23 }
                }
                // TODO NOW: other types UROW UCOLUMN
            }
        }
    }
}
