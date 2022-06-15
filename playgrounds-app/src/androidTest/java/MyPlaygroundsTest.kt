import android.graphics.*
import android.util.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.mareklangiewicz.playgrounds.MainActivity
import pl.mareklangiewicz.uspek.*
import java.io.*

@RunWith(AndroidJUnit4::class)
class MyPlaygroundsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun DisplayStuff() { composeTestRule.run {
        onNodeWithText("Playgrounds").performClick()
        onAllNodesWithText("GestureAnimationSample", substring = true).assertCountEquals(2)
        onNodeWithText("AnimateFloatSample", substring = true).assertIsDisplayed()
    } }

    @Test
    fun DoScreenshot() { composeTestRule.run {
        val bitmap = onRoot().captureToImage().asAndroidBitmap()
        saveScreenshot("SchoolShot_01_" + System.currentTimeMillis().toString(), bitmap)
    } }

    @Test
    fun ChangeSelectedSample() = composeTestRule.run {
        onNodeWithText("Playgrounds").performClick()
        onAllNodesWithText("DoubleTapToLikeSample", substring = true).assertCountEquals(1)
        onNodeWithText("DoubleTapToLikeSample", substring = true).performClick()
        onAllNodesWithText("DoubleTapToLikeSample", substring = true).assertCountEquals(2)

//        composeTestRule.onNodeWithText("sth").assertIsFocused() // TODO: play with this
        Unit
    }

    @Test
    fun uspekStuff() {
        uspekLog = {
            if (it.failed) Log.e("uspekLog", it.status, it.end?.cause)
            else Log.w("uspekLog", it.status)
        }
        uspek {
            with(composeTestRule) {
                "On default first tab" o {
                    onAllNodesWithText("School").assertCountEquals(3)
                }
            }
        }
    }


    private fun saveScreenshot(filename: String, bmp: Bitmap) {
        val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath
        FileOutputStream("$path/$filename.png").use { out ->
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        println("Saved screenshot to $path/$filename.png")
    }
}