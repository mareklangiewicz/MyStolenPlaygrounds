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

    @USpekTestTree(12) fun layout() = rule.layout()
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
        var withBox1Cyan by mutableStateOf(false)
        var withBox2Red by mutableStateOf(false)
        var withBox3Green by mutableStateOf(false)
        var withBox4Blue by mutableStateOf(false)
        setContent {
            MyExaminedLayout1(
                type = type,
                withBox1Cyan = withBox1Cyan,
                withBox2Red = withBox2Red,
                withBox3Green = withBox3Green,
                withBox4Blue = withBox4Blue,
                report = ::report
            )
        }
        waitForIdle()

        val rigidBoxDp = 400.dp
        val rigidBoxPx = rigidBoxDp.roundToPx()

        "With type UBOX" o {

            "With no child boxes" o {

                "only root rigid box is measured and placed" o { reports.size eq 3 }

                "whole layout content is fixed to 400.dp.square box" o {
                    reports[0] eq ("rigid box measure with" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                    reports[1] eq ("rigid box measured" to PlaceableData(rigidBoxPx, rigidBoxPx))
                }
                "whole layout content is placed and attached" o {
                    val (key, data) = reports[2]
                    key eq "rigid box placed"
                    data as LayoutCoordinatesData
                    data.size eq rigidBoxPx.square
                    data.isAttached eq true
                }
            }

            "When cyan box gets enabled" o {
                withBox1Cyan = true
                waitForIdle()

                val cyanBoxPx = 160.dp.roundToPx()
                "cyan box gets measured and placed" o {
                    reports[3] eq ("cyan box outer measure with" to Constraints(0, rigidBoxPx, 0, rigidBoxPx))
                    reports[4] eq ("cyan box inner measure with" to Constraints.fixed(cyanBoxPx, cyanBoxPx))
                    reports[5] eq ("cyan box inner measured" to PlaceableData(cyanBoxPx, cyanBoxPx))
                    reports[6] eq ("cyan box outer measured" to PlaceableData(cyanBoxPx, cyanBoxPx))
                    reports[7].first eq "cyan box outer placed" // TODO_later: check LayoutConstraintsData
                    reports[8].first eq "cyan box inner placed" // TODO_later: check LayoutConstraintsData
                }
                "rigid box gets remeasured and placed the same way as before" o {
                    reports[9] eq reports[0]
                    reports[10] eq reports[1]
                    reports[11] eq reports[2]
                    reports.size eq 12
                }

                "When blue box gets enabled" o {
                    withBox4Blue = true
                    waitForIdle()

                    "blue box gets measured and placed with fixed rigid box size" o {
                        reports[12] eq ("blue box outer measure with" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                        reports[13] eq ("blue box inner measure with" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                        reports[14] eq ("blue box inner measured" to PlaceableData(rigidBoxPx, rigidBoxPx))
                        reports[15] eq ("blue box outer measured" to PlaceableData(rigidBoxPx, rigidBoxPx))
                        reports[16].first eq "blue box outer placed" // TODO_later: check LayoutConstraintsData
                        reports[17].first eq "blue box inner placed" // TODO_later: check LayoutConstraintsData
                    }

                    "rigid box gets remeasured again and placed the same way as before" o {
                        reports[18] eq reports[0]
                        reports[19] eq reports[1]
                        reports[20] eq reports[2]
                        reports.size eq 21
                    }
                }
            }
        }
    }
}
