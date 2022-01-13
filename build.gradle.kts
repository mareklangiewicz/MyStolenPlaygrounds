import okio.FileSystem.Companion.SYSTEM
import okio.Path.Companion.toPath
import okio.Path
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE
import pl.mareklangiewicz.deps.*

//val androidxRootDir = "/home/marek/code/android/androidx-main".toPath()
val androidxRootDir = "/home/marek/code/kotlin/compose-jb/compose".toPath()
val androidxSupportDir = androidxRootDir / "frameworks/support"
val srcKotlinDir = project.rootOkioPath / "lib1/src/main/kotlin"
val srcJavaDir = project.rootOkioPath / "lib1/src/main/java"
val stolenSrcKotlinDir = srcKotlinDir / "stolen"
val stolenSrcJavaDir = srcJavaDir // java files have to be in directories same as packages :(
val stolenAndroTestsDir = project.rootOkioPath / "lib1/src/androidTest/kotlin"
val templatesSrcKotlinDir = srcKotlinDir / "templates"

task("doStealComposeStuff") {
    doLast {
        stealComposeStuff()
    }
}

fun stealComposeStuff() {
    stealComposeSources()
    stealComposeTests()
    stealAndProcessComposeSamples()
}

fun stealComposeSources() {
    stealJavaSources("compose/ui/ui-android-stubs/src/main/java/android/view", "android/view")
    stealSources("annotation/annotation-sampled/src/main/java/androidx/annotation", "androidx-annotation")
    stealSources("compose/test-utils/src/commonMain/kotlin/androidx/compose/testutils", "compose-testutils")
    stealSources("compose/test-utils/src/androidMain/kotlin/androidx/compose/testutils", "compose-testutils") { "Screenshot" !in it.name }
}

fun stealComposeTests() {
    stealAndroTests("compose/foundation/foundation/src/androidAndroidTest/kotlin/androidx/compose/foundation", "foundation-tests") { file ->
        "CanvasTest" in file.name || "Foundation" in file.name
    }
}

fun stealAndProcessComposeSamples() {

    val samples = mutableListOf<Pair<String, Path?>>() // funName to filePath
    samples.stealComposeSamples()

    val interpolations = mapOf(
//            stolenSrcKotlinDir.toString() to "stolenSrcKotlinDir",
//            templatesSrcKotlinDir.toString() to "templatesSrcKotlinDir",
        srcKotlinDir.toString() to "srcKotlinDir",
    )
    processTemplates(templatesSrcKotlinDir, samples, interpolations)
}

fun MutableCollection<Pair<String, Path?>>.stealComposeSamples() {
    stealSamples("compose/ui/ui/samples/src/main/java/androidx/compose/ui/samples", "ui-samples")
    stealSamples("compose/ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples", "ui-graphics-samples")
    stealSamples("compose/animation/animation-core/samples/src/main/java/androidx/compose/animation/core/samples", "animation-core-samples")
    stealSamples("compose/animation/animation/samples/src/main/java/androidx/compose/animation/samples", "animation-samples")
}

fun stealSources(supportDir: String, stolenDir: String, filter: (input: Path) -> Boolean = { true }) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenSrcKotlinDir / stolenDir
) { input, _, content -> if (filter(input)) content.withOptionalRImport() else null }

fun stealAndroTests(supportDir: String, stolenDir: String, filter: (input: Path) -> Boolean = { true }) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenAndroTestsDir / stolenDir
) { input, _, content -> if (filter(input)) content.withOptionalRImport() else null }

fun stealJavaSources(supportDir: String, stolenDir: String) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenSrcJavaDir / stolenDir
) { _, _, content -> content }

fun MutableCollection<Pair<String, Path?>>.stealSamples(
    supportDir: String,
    stolenDir: String
) = SYSTEM.processEachFile(
        androidxSupportDir / supportDir,
        stolenSrcKotlinDir / stolenDir
    ) { _, outputPath, content ->
        val pkg = content.findPackage()
        val newSamples = content.findSampledComposableFunNames().map { "$pkg.$it" to outputPath }
        this += newSamples
        content.withOptionalRImport()
    }

fun String.findPackage(): String = urePackageLine.compile().find(this)!!["thePackage"]

fun String.withOptionalRImport(): String {
    ureDefaultRUsage.compile().containsMatchIn(this) || return this
    val r = ureContentWithImports.compile().matchEntire(this) ?: error("imports not found in:\n$this")
    return r["partBeforeImports"] +
            r["partWithImports"] +
            "import pl.mareklangiewicz.playgrounds.R\n" +
            r["partAfterImports"]
}

fun processTemplates(
    templatesDir: Path,
    samples: Collection<Pair<String, Path?>>,
    interpolations: Map<String, String>
) = SYSTEM.processEachFile(templatesDir, templatesDir) { _, _, templateFileContent ->

    val r = ureContentWithTemplate.compile().matchEntire(templateFileContent) ?: error("matchEntire failed")

    val newGenerationArea = processFunTemplate(r["partTemplate"], r["funName"], samples, interpolations)

    r["partBeforeTemplate"] +
            r["partTemplate"] +
            r["partBeforeGenerationArea"] +
            r["partBeginGenerationAreaMarker"] +
            newGenerationArea +
            r["partEndGenerationAreaMarker"] +
            r["partAfterGenerationArea"]
}

fun String.findSampledComposableFunNames() =
    ureSampledFunHeader
        .compile()
        .findAll(this)
        .map { it.groups["funName"]!!.value.also { println("found fun: $it") } }

fun processFunTemplate(
    template: String,
    templateFunName: String,
    samples: Collection<Pair<String, Path?>>,
    interpolations: Map<String, String>
) =
    template
        .replace(templateFunName + "Template", templateFunName)
        .replace(ureWholeLineWith("// REMOVE").compile(), "")
        .replace(ureWholeLineWith("// REPLACE").compile(),
            samples
                .joinToString(
                    prefix = " ".repeat(8),
                    separator = "\n" + " ".repeat(8),
                    postfix = "\n",
                    transform = { (name, path) ->
                        val pathStr = if (path == null) "null" else "\"${path.toShortStr(interpolations)}\""
                        "MySampleData(\"$name\", $pathStr) { $name() },"
                    }
                )
        )

fun String.interpolate(vararg interpolations: Pair<String, String>) = interpolate(interpolations.toMap())
fun String.interpolate(interpolations: Map<String, String>) =
    interpolations.keys.fold(this) { acc, key -> acc.replace(key, "\\\${${interpolations[key]!!}}") }
fun Path.toShortStr(interpolations: Map<String, String>) = toString().interpolate(interpolations)



val ureSampledFunHeader = ure(MULTILINE) {
    1 of BOL
    1 of ir("@Sampled")
    1..MAX of space
    1 of ir("@Composable")
    1..MAX of space
    1 of ir("fun")
    1..MAX of space
    1 of ure("funName") {
        1 of posixUpper
        0..MAX of (word or digit)
    }
    1 of ir("\\(\\)")
}


fun ureWholeLineWith(text: UreIR) = ure(MULTILINE) {
    1 of BOL
    0..MAX of any
    1 of ir(text) // TODO_later: maybe force literal texts
    0..MAX of any
    1 of EOL
    1 of lf
}


val ureBigIdent = ure {
    1 of posixUpper
    0..MAX of (word or digit)
}

val ureParamsNotNested = ure { // I assume no internal expressions with parenthesis
    1 of ch("\\(")
    0..MAX of oneCharNotOf("(")
    1 of ch("\\)")
}

val ureAnnotations = ure {
    1..MAX of { // single annotation
        1 of ch("@")
        1 of ureBigIdent
        0..1 of ureParamsNotNested
        1..MAX of space
    }
}

val ureAnnotationsWithComposable = ure {
    1 of ureAnnotations
    1 of lookBehind {
        1 of ir("@Composable")
        1..100 of space
    }
}

val ureMaybeSomeSpaces = ure { 0..MAX of ch(" ") }

val ureIndentedNotEmptyLineContent = ure {
    1 of ch(" ")
    1 of ureMaybeSomeSpaces
    1 of oneCharNotOf(" ", "\\n")
    0..MAX of oneCharNotOf("\\n")
}

val ureIndentedLine = ure(MULTILINE) {
    1 of BOL
    1 of (ureIndentedNotEmptyLineContent or ureMaybeSomeSpaces)
    1 of lf
}

val ureComposableFunTemplate = ure(MULTILINE) {
    1 of ureAnnotationsWithComposable
    0..MAX of space // annotations can contain ending spaces too
    1 of BOL // we have to start from new line to easier find ending brace }
    1 of ir("fun")
    1 of space
    1 of ure("funName") { 1 of ureBigIdent }
    1 of ir("Template")
    1 of ureParamsNotNested
    1 of space
    1 of ch("\\{")
    1 of lf
    1..MAX of ureIndentedLine
    1 of BOL
    1 of ch("\\}")
    1 of lf
}

val ureBeginGenerationAreaMarker = ure(MULTILINE) {
    1 of BOL
    1 of ir("// BEGIN generated ")
    1 of ref(name = "funName")
    1 of ir(" from ")
    1 of ref(name = "funName")
    1 of ir("Template")
    1 of lf
}

val ureEndGenerationAreaMarker = ure(MULTILINE) {
    1 of BOL
    1 of ir("// END generated ")
    1 of ref(name = "funName")
    1 of ir(" from ")
    1 of ref(name = "funName")
    1 of ir("Template")
    1 of lf

}

val ureContentWithTemplate = ure {
    1 of ure("partBeforeTemplate", DOT_MATCHES_ALL) { x(0..MAX, reluctant = true) of any }
    1 of ure("partTemplate") { 1 of ureComposableFunTemplate }
    1 of ure("partBeforeGenerationArea", DOT_MATCHES_ALL) { 0..MAX of any }
    1 of ure("partBeginGenerationAreaMarker") { 1 of ureBeginGenerationAreaMarker }
    1 of ure("partGenerationArea", DOT_MATCHES_ALL) { 0..MAX of any }
    1 of ure("partEndGenerationAreaMarker")  { 1 of ureEndGenerationAreaMarker }
    1 of ure("partAfterGenerationArea", DOT_MATCHES_ALL) { 0..MAX of any }
}

val urePackageLine = ure(MULTILINE) {
    1 of BOL
    1 of ir("package ")
    1 of ure("thePackage") {
        1..MAX of nonSpace
    }
    1 of EOL
}

val ureDefaultRUsage = ure {
    1 of lookBehind(positive = false) { 1 of dot }
    1 of wordBoundary
    1 of ch("R")
    1 of dot
    1 of word
}

val ureContentWithImports = ure {
    1 of ure("partBeforeImports", DOT_MATCHES_ALL) { x(0..MAX, reluctant = true) of any }
    1 of ure("partWithImports") {
        1..MAX of ure(MULTILINE) {
            1 of BOL
            1 of ir("import ")
            1..MAX of any
            1 of lf
        }
    }
    1 of ure("partAfterImports", DOT_MATCHES_ALL) { 0..MAX of any }

}