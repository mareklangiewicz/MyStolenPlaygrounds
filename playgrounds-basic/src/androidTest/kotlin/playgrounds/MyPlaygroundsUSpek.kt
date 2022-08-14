package pl.mareklangiewicz.playgrounds

import androidx.compose.ui.test.junit4.*
import org.junit.*
import org.junit.runner.*
import pl.mareklangiewicz.ulog.*
import pl.mareklangiewicz.uspek.*
import pl.mareklangiewicz.uwidgets.*

@RunWith(USpekJUnit4Runner::class)
class MyPlaygroundsUSpek {

    init {
        uspekLog = { ulogw("uspek ${it.status}") }
    }

    @get:Rule val rule = createComposeRule()

    @USpekTestTree(23) fun melusf() = rule.MyExaminedLayoutUSpekFun()
}