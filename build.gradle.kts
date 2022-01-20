import okio.FileSystem.Companion.SYSTEM
import okio.Path.Companion.toPath
import okio.Path
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.ure.*

//val androidxRootDir = "/home/marek/code/android/androidx-main".toPath()
val androidxRootDir = "/home/marek/code/kotlin/compose-jb/compose".toPath()
val androidxSupportDir = androidxRootDir / "frameworks/support"
val srcLib1KotlinDir = project.rootOkioPath / "lib1/src/main/kotlin"
val srcLibUiSamplesKotlinDir = project.rootOkioPath / "lib-ui-samples/src/main/kotlin"
val srcAppKotlinDir = project.rootOkioPath / "app/src/main/kotlin"
val srcJavaDir = project.rootOkioPath / "lib1/src/main/java"
val stolenSrcKotlinDir = srcLib1KotlinDir / "stolen"
val stolenSrcJavaDir = srcJavaDir // java files have to be in directories same as packages :(
val stolenSamplesKotlinDir = srcLibUiSamplesKotlinDir / "stolen"
val stolenAndroTestsDir = project.rootOkioPath / "lib1/src/androidTest/kotlin/stolen"
val templatesSrcKotlinDir = srcAppKotlinDir / "templates"

task("doStealComposeStuff") {
    group = "steal"
    doLast {
        stealComposeStuff()
    }
}

fun stealComposeStuff() {
    stealComposeAnnotations()
    stealComposeSources()
    stealComposeTests()
    stealAndProcessComposeSamples()
}

fun stealComposeAnnotations() {
    stealAnnotations("annotation/annotation-sampled/src/main/java/androidx/annotation", "androidx-annotation")
}
fun stealComposeSources() {
    stealJavaSources("compose/ui/ui-android-stubs/src/main/java/android/view", "android/view")
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
        srcLibUiSamplesKotlinDir.toString() to "samplesDir",
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
) { input, _, content -> if (filter(input)) content else null }

fun stealAnnotations(supportDir: String, stolenDir: String, filter: (input: Path) -> Boolean = { true }) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenSamplesKotlinDir / stolenDir
) { input, _, content -> if (filter(input)) content else null }

fun stealAndroTests(supportDir: String, stolenDir: String, filter: (input: Path) -> Boolean = { true }) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenAndroTestsDir / stolenDir
) { input, _, content -> if (filter(input)) content else null }

fun stealJavaSources(supportDir: String, stolenDir: String) = SYSTEM.processEachFile(
    inputRootDir = androidxSupportDir / supportDir,
    outputRootDir = stolenSrcJavaDir / stolenDir
) { _, _, content -> content }

fun MutableCollection<Pair<String, Path?>>.stealSamples(
    supportDir: String,
    stolenDir: String
) = SYSTEM.processEachFile(
        androidxSupportDir / supportDir,
        stolenSamplesKotlinDir / stolenDir
    ) { _, outputPath, content ->
        val pkg = content.findPackage()
        val newSamples = content.findSampledComposableFunNames().map { "$pkg.$it" to outputPath }
        this += newSamples
        content
    }

fun String.findPackage(): String = urePackageLine.compile().find(this)!!["thePackage"]

@Deprecated("Prefer multiple modules so default R class matches source file package")
fun String.withOptionalRImport(): String {
    ureDefaultRUsage.compile().containsMatchIn(this) || return this
    val r: MatchResult = ureContentWithImports.compile().matchEntire(this) ?: error("imports not found in:\n$this")
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

    val r = ureContentWithTemplate.compile().matchEntire(templateFileContent) ?: error("No template")
    val partBeforeTemplate by r
    val partTemplate by r
    val partBeforeGenerationRegion by r
    val partAfterGenerationRegion by r
    val funName by r
    val regionName = "Generated " + funName + " from " + funName + "Template"
    val newGenerationArea = processFunTemplate(partTemplate, funName, samples, interpolations)
    val wholeRegion = "\n// region $regionName\n$newGenerationArea// endregion $regionName\n\n"
    val templateRelatedStuff = partBeforeTemplate + partTemplate
    val regionRelatedStuff = partBeforeGenerationRegion + wholeRegion + partAfterGenerationRegion
    templateRelatedStuff + regionRelatedStuff
}

fun String.findSampledComposableFunNames(): Sequence<String> =
    ureSampledFunHeader
        .compile()
        .findAll(this)
        .map { result: MatchResult -> result["funName"].also { println("found fun: $it") } }

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



val ureSampledFunHeader = ure {
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


fun ureWholeLineWith(text: UreIR) = ure {
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

val ureIndentedLine = ure {
    1 of BOL
    1 of (ureIndentedNotEmptyLineContent or ureMaybeSomeSpaces)
    1 of lf
}

val ureComposableFunTemplate = ure {
    1 of ureAnnotationsWithComposable
    0..MAX of space // annotations can contain ending spaces too
    1 of BOL // we have to start from new line to easier find ending brace }
    1 of ir("fun")
    1 of space
    1 of ure { 1 of ureBigIdent }.withName("funName")
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

val ureContentWithTemplate = ure {
    val regionName = ure {
        1 of ir("Generated ")
        1 of ref(name = "funName") // backreference to actual template function name (inside ureComposableFunTemplate)
        1 of ir(" from ")
        1 of ref(name = "funName") // backreference to actual template function name again
        1 of ir("Template")
    }
    1 of ureWhateva().withName("partBeforeTemplate")
    1 of ure { 1 of ureComposableFunTemplate }.withName("partTemplate")
    1 of ureWhateva().withName("partBeforeGenerationRegion")
    1 of ureRegion(ureWhateva(), regionName = regionName)
    1 of ureWhateva(reluctant = false).withName("partAfterGenerationRegion")
}

val urePackageLine = ure {
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
    1 of ureWhateva().withName("partBeforeImports")
    1 of ure {
        1..MAX of ure {
            1 of BOL
            1 of ir("import ")
            1..MAX of any
            1 of lf
        }
    }.withName("partWithImports")
    1 of ureWhateva().withName("partAfterImports")
}