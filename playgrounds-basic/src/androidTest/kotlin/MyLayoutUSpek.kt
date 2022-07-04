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

    @USpekTestTree(10) fun layout() = rule.layout()
}

private val reports = mutableStateListOf<Pair<String, Any>>()
private fun report(v: Pair<String, Any>) {
    Log.d("uspek report", "${v.first}: ${v.second}")
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

        val rigidBoxDp = 400.dp
        val rigidBoxPx = rigidBoxDp.roundToPx()

        "With type UBOX" o {

            "With no child boxes" o {

                "only outer rigid box is measured" o { reports.size eq 2 }

                "whole layout content is fixed to 400.dp.square box" o {
                    reports[0] eq ("rigid box incoming constraints" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                    reports[1] eq ("rigid box measured place info" to PlaceInfo(rigidBoxPx, rigidBoxPx, rigidBoxPx, rigidBoxPx))
                }
            }

            "When cyan box gets enabled" o {
                withBox1Cyan = true
                waitForIdle()

                val cyanBoxPx = 160.dp.roundToPx()
                "cyan box gets measured" o {
                    reports[2] eq ("cyan box outer incoming constraints" to Constraints(0, rigidBoxPx, 0, rigidBoxPx))
                    reports[3] eq ("cyan box inner incoming constraints" to Constraints.fixed(cyanBoxPx, cyanBoxPx))
                    reports[4] eq ("cyan box inner measured place info" to PlaceInfo(cyanBoxPx, cyanBoxPx, cyanBoxPx, cyanBoxPx))
                    reports[5] eq ("cyan box outer measured place info" to PlaceInfo(cyanBoxPx, cyanBoxPx, cyanBoxPx, cyanBoxPx))
                }
                "rigid box gets remeasured same way as before" o {
                    reports[6] eq reports[0]
                    reports[7] eq reports[1]
                    reports.size eq 8
                }

                "When blue box gets enabled" o {
                    withBox4Blue = true
                    waitForIdle()

                    "blue box gets measured with fixed rigid box size" o {
                        reports[8] eq ("blue box outer incoming constraints" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                        reports[9] eq ("blue box inner incoming constraints" to Constraints.fixed(rigidBoxPx, rigidBoxPx))
                        reports[10] eq ("blue box inner measured place info" to PlaceInfo(rigidBoxPx, rigidBoxPx, rigidBoxPx, rigidBoxPx))
                        reports[11] eq ("blue box outer measured place info" to PlaceInfo(rigidBoxPx, rigidBoxPx, rigidBoxPx, rigidBoxPx))
                    }
                }
            }
        }
    }
}
