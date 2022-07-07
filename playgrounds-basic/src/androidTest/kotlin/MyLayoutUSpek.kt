package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.test.junit4.*
import androidx.compose.ui.unit.*
import org.junit.*
import org.junit.runner.*
import pl.mareklangiewicz.uspek.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import kotlin.Pair

@RunWith(USpekJUnit4Runner::class) class LayoutUSpek {
    init {
        uspekLog = {
            if (it.failed) Log.e("uspek", it.status)
            else Log.w("uspek", it.status)
        }
    }

    @get:Rule val rule = createComposeRule()

    @USpekTestTree(17) fun layout() = rule.layout()
}

private val reports = mutableStateListOf<Pair<String, Any>>()
private fun report(v: Pair<String, Any>) {
    Log.d("rspek", "${v.first}: ${v.second.str}") // rspek so I can filter logs with uspek/rspek/spek
    reports.add(v)
}

fun ComposeContentTestRule.layout() = with(density) {

    reports.clear()

    "On MyLayoutExaminedLayout1" o {
        var type by mutableStateOf(UBOX)
        var rigidSizeDp by mutableStateOf(400.dp.square)
        val rigidSizePx = rigidSizeDp.toSize().roundToIntSize()
        var withBox1Cyan by mutableStateOf(false)
        var withBox2Red by mutableStateOf(false)
        var withBox3Green by mutableStateOf(false)
        var withBox4Blue by mutableStateOf(false)
        setContent {
            MyExaminedLayout1(
                type = type,
                size = rigidSizeDp,
                withBox1Cyan = withBox1Cyan,
                withBox2Red = withBox2Red,
                withBox3Green = withBox3Green,
                withBox4Blue = withBox4Blue,
                report = ::report
            )
        }
        waitForIdle()

        "With rigid container type UBOX" o {

            "With no child boxes" o {

                "only root rigid container is measured and placed" o { reports.size eq 3 }

                "rigid container is fixed to 400.dp.square box" o {
                    reports[0] eq ("rigid container measure with" to rigidSizePx.toFixedConstraints())
                    reports[1] eq ("rigid container measured" to rigidSizePx.toPlaceableData())
                }
                "rigid container is placed and attached" o {
                    val (key, data) = reports[2]
                    key eq "rigid container placed"
                    data as LayoutCoordinatesData // FIXME NOW: check details??
                    data.size eq rigidSizePx
                    data.isAttached eq true
                    reports.size eq 3
                }
            }

            "When cyan box gets enabled" o {
                withBox1Cyan = true
                waitForIdle()

                "rigid container starts measure again with the same constraints" o { reports[3] eq reports[0] }

                val cyanBoxSizePx = 160.dp.square.toSize().roundToIntSize()
                "cyan box gets measured" o {
                    reports[4] eq ("cyan box outer measure with" to rigidSizePx.toMaxConstraints())
                    reports[5] eq ("cyan box inner measure with" to cyanBoxSizePx.toFixedConstraints())
                    reports[6] eq ("cyan box inner measured" to cyanBoxSizePx.toPlaceableData())
                    reports[7] eq ("cyan box outer measured" to cyanBoxSizePx.toPlaceableData())
                }

                "rigid container gets remeasured and placed the same way as before" o {
                    reports[8] eq reports[1]
                    reports[9] eq reports[2]
                }

                "cyan box gets placed" o {
                    reports[10].first eq "cyan box outer placed" // TODO_NOW: check LayoutConstraintsData
                    reports[11].first eq "cyan box inner placed" // TODO_NOW: check LayoutConstraintsData
                }

                "rigid container starts measure again with the same constraints" o { reports[12] eq reports[0] }

                "When blue box gets enabled" o {
                    withBox4Blue = true
                    waitForIdle()

                    "blue box gets measured" o {
                        reports[13] eq ("blue box outer measure with" to rigidSizePx.toFixedConstraints())
                        reports[14] eq ("blue box inner measure with" to rigidSizePx.toFixedConstraints())
                        reports[15] eq ("blue box inner measured" to rigidSizePx.toPlaceableData())
                        reports[16] eq ("blue box outer measured" to rigidSizePx.toPlaceableData())
                    }

                    "rigid container gets remeasured and placed the same way as before" o {
                        reports[17] eq reports[1]
                        reports[18] eq reports[2]
                    }
                    "cyan box gets placed again" o {
                        reports[19].first eq "cyan box outer placed" // TODO_NOW: check LayoutConstraintsData
                        reports[20].first eq "cyan box inner placed" // TODO_NOW: check LayoutConstraintsData
                    }
                    "blue box gets placed with fixed rigid box size" o {
                        reports[21].first eq "blue box outer placed" // TODO_later: check LayoutConstraintsData
                        reports[22].first eq "blue box inner placed" // TODO_later: check LayoutConstraintsData
                    }

                    "no other reports" o { reports.size eq 23 }
                }
            }
        }
    }
}
