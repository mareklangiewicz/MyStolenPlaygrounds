@file:OptIn(okio.ExperimentalFileSystem::class)
import okio.Path.Companion.toPath
import kotlin.text.RegexOption.MULTILINE

task("stealAndroidxComposeUiGraphicsSamples") {
    doLast {
        val androidxRootDir = "/home/marek/code/android/androidx-main".toPath()
        val androidxSupportDir = androidxRootDir / "frameworks/support"
        val stolenSrcKotlinDir = project.rootOkioPath / "app/src/main/kotlin/stolen"

        // just copy the @Sampled annotation definition
        processAllKtFiles(
            inputRootDir = androidxSupportDir / "annotation/annotation-sampled/src/main/java/androidx/annotation",
            outputRootDir = stolenSrcKotlinDir / "androidx-annotation"
        ) { it }

        val sampleFunNames = mutableListOf<String>()

        // copy samples and collect @Sampled @Composable fun names
        processAllKtFiles(
            inputRootDir = androidxSupportDir / "compose/ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples",
            outputRootDir = stolenSrcKotlinDir / "ui-graphics-samples"
        ) { sampleFileContent ->
            println("processing next file...")
            val foundNames = sampleFileContent.findSampledComposableFunNames()
            sampleFunNames += foundNames
            println("found fun samples: ${foundNames.toList()}")
            sampleFileContent
        }
        println("TODO: use collected fun names and template file") // TODO NOW
    }
}

fun String.findSampledComposableFunNames(): Sequence<String> {
    val ureFunHeader = ure {
        1 of BOL
        1 of ir("@Sampled")
        1..MAX of space
        1 of ir("@Composable")
        1..MAX of space
        1 of ir("fun")
        1..MAX of space
        1 of named("funName") {
            1 of posixUpper
            0..MAX of (word or digit)
        }
        1 of ir("\\(\\)")
    }

    return ureFunHeader.compile(MULTILINE)
        .findAll(this)
        .map { it.groups["funName"]!!.value }
}
