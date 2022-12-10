package pl.mareklangiewicz.playgrounds

import androidx.compose.ui.test.junit4.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.*
import pl.mareklangiewicz.udemo.*
import pl.mareklangiewicz.ulog.*
import pl.mareklangiewicz.uspek.*

@RunWith(USpekJUnit4Runner::class)
class MyPlaygroundsUSpek {
    init { uspekLog = { ulogw("uspek ${it.status}") } }
    @USpekTestTree(33) fun melusf() = runUComposeTest { MyExaminedLayoutUSpekFun() }
}