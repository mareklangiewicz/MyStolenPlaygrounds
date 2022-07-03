package pl.mareklangiewicz.playgrounds

import android.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.test.junit4.*
import org.junit.*
import org.junit.runner.*
import pl.mareklangiewicz.uspek.*
import pl.mareklangiewicz.uwidgets.UContainerType.*
import java.lang.Thread.*
import kotlin.Pair

@RunWith(USpekJUnit4Runner::class)
class LayoutUSpek {
    init {
        uspekLog = {
            if (it.failed) Log.e("uspek", it.status)
            else Log.w("uspek", it.status)
        }
    }

    @get:Rule
    val rule = createComposeRule()

    @USpekTestTree(2)
    fun layout() = rule.layout()
}

private val reports = mutableStateListOf<Pair<String, Any>>()
private fun report(v: Pair<String, Any>) {
    Log.d("kkk report", "${v.first}: ${v.second}")
    reports.add(v)
}

fun ComposeContentTestRule.layout() {

    "On MyLayoutExaminedLayout1 UBOX" o {
        var withBox1Cyan by mutableStateOf(false)
        var withBox2Red by mutableStateOf(false)
        var withBox3Green by mutableStateOf(false)
        var withBox4Blue by mutableStateOf(false)
        setContent {
            MyExaminedLayout1(
                type = UBOX,
                withBox1Cyan = withBox1Cyan,
                withBox2Red = withBox2Red,
                withBox3Green = withBox3Green,
                withBox4Blue = withBox4Blue,
                report = ::report
            )
        }

        "On 2 boxes enabled" o {
            waitForIdle()
            sleep(2000)
            withBox1Cyan = true
            waitForIdle()
            sleep(2000)
            withBox2Red = true
            waitForIdle()
            sleep(2000)
            withBox3Green = true
            waitForIdle()
            sleep(2000)
            withBox4Blue = true
            waitForIdle()
            sleep(2000)
        }
    }
}
