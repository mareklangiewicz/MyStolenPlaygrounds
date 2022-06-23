import android.util.*
import androidx.compose.foundation.*
import org.junit.runner.*
import org.junit.runners.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.test.junit4.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runners.model.*
import pl.mareklangiewicz.uspek.*
import java.lang.Thread.*
import java.lang.reflect.*

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

    @Test fun simpleTest() = assertEquals(2, 2)
    @Test fun simpleFailingTest() = assertEquals(3, 4)

    @USpekTestTree(6)
    fun layoutUSpek() = with(rule) {
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
}

private val FrameworkMethod.expectedUSpekTestCount get() = getAnnotation(USpekTestTree::class.java)?.expectedCount

class USpekJUnit4Runner(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {
    override fun getChildren(): List<FrameworkMethod> = buildList {
        addAll(testClass.getAnnotatedMethods(Test::class.java))
        val uspekTestsMethods = testClass.getAnnotatedMethods(USpekTestTree::class.java)
        for (method in uspekTestsMethods) {
            for (i in 1..method.expectedUSpekTestCount!! + 1) // + 1 to additional check if no tests left to invoke
                add(USpekFrameworkMethod(method.method, i))
        }
    }

    override fun testName(method: FrameworkMethod): String = (method as? USpekFrameworkMethod)?.uspekTestName ?: method.name
}

private class USpekFrameworkMethod(method: Method, val testNr: Int): FrameworkMethod(method) {

    val expectedCount = expectedUSpekTestCount!!

    override fun equals(other: Any?) = this === other
    override fun hashCode() = super.hashCode() xor testNr

    val uspekTestName get() = super.getName() + when {
        testNr <= expectedCount -> " $testNr of $expectedCount"
        testNr == expectedCount + 1 -> " check if no tests left"
        else -> error("Incorrect number of framework methods generated.")
    }

    override fun invokeExplosively(target: Any?, vararg params: Any?) {
        check(params.isEmpty())
        invokeInUSpek(target)
    }

    @Synchronized
    private fun invokeInUSpek(target: Any?): Unit = with(GlobalUSpekContext) {
        try {
            branch = root
            super.invokeExplosively(target)
            assertTrue("Expected $expectedCount tests, but nr $testNr is not found", testNr == expectedCount + 1)
        } catch (e: USpekException) {
            branch.end = e
            uspekLog(branch)
            if (branch.failed) throw e.cause!!
            assertTrue("Expected $expectedCount tests, but looks like there is more.", testNr <= expectedCount)
        }
    }
}

@Target(AnnotationTarget.FUNCTION)
annotation class USpekTestTree(val expectedCount: Int) {

}