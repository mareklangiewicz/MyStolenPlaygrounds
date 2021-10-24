import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.mareklangiewicz.playgrounds.MainActivity

@RunWith(AndroidJUnit4::class)
class MyPlaygroundsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun DisplayStuff() {
        composeTestRule.onNodeWithText("AlphaSample").assertIsDisplayed()
        composeTestRule.onNodeWithText("AlignmentLineSample").assertIsDisplayed()
    }

    @Test
    fun ChangeSelectedSample() {
        composeTestRule.onAllNodesWithText("AlignmentLineSample").assertCountEquals(2)
        composeTestRule.onNodeWithText("AlphaSample").performClick()
        composeTestRule.onAllNodesWithText("AlignmentLineSample").assertCountEquals(1)

//        composeTestRule.onNodeWithText("sth").assertIsFocused() // TODO: play with this
//        composeTestRule.onNodeWithText("sth").assertIsSelected() // TODO: and this
    }
}