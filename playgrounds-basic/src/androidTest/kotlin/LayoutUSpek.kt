import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.test.junit4.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import pl.mareklangiewicz.uspek.*
import java.lang.Thread.*

@RunWith(USpekJUnit4Runner::class)
class LayoutUSpek {
    init {
        uspekLog = {
            if (it.failed) Log.e("uspek", it.status)
            else Log.w("uspek", it.status)
        }
    }

    @get:Rule val rule = createComposeRule()

    @USpekTestTree(6) fun layout() = rule.testLayout()
}

fun ComposeContentTestRule.testLayout() {
    "On simple box content" o {
        setContent {
            Box {
                Text("First simple box")
            }
        }
    }
    "On second nothing test" o {
        setContent {
            Box(Modifier.background(Color.Blue)) {
                Text("Second simple box")
            }
        }
        sleep(1800)
        assertEquals(4, 4)
    }
    "On third nothing test" o {
        assertEquals(5, 5)
        "On inner UI test" o {
            setContent {
                Box(Modifier.background(Color.Cyan)) {
                    Text("Third inner box")
                }
            }
            "wait a bit with content and fail" o {
                sleep(1000)
                fail()
            }
            "wait a bit again with content and finish" o {
                sleep(1000)
            }
        }
    }
}