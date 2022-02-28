import okio.FileSystem.Companion.SYSTEM
import okio.Path.Companion.toPath
import okio.Path
import okio.Path.Companion.toOkioPath
import pl.mareklangiewicz.ure.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.sourcefun.*

plugins {
    id("pl.mareklangiewicz.sourcefun")
}

//val rootAndroidxPath = "/home/marek/code/android/androidx-main".toPath()
val rootAndroidxPath = "/home/marek/code/kotlin/compose-jb/compose".toPath()
val androidxSupportDir = rootAndroidxPath / "frameworks/support"
val srcLib1KotlinDir = rootProjectPath / "lib1/src/main/kotlin"
val srcLibUiSamplesKotlinDir = rootProjectPath / "lib-ui-samples/src/main/kotlin"
val srcAppKotlinDir = rootProjectPath / "app/src/main/kotlin"
val srcJavaDir = rootProjectPath / "lib1/src/main/java"
val stolenSrcKotlinDir = srcLib1KotlinDir / "stolen"
val stolenSrcJavaDir = srcJavaDir // java files have to be in directories same as packages :(
val stolenSamplesKotlinDir = srcLibUiSamplesKotlinDir / "stolen"
val stolenAndroTestsDir = rootProjectPath / "lib1/src/androidTest/kotlin/stolen"
val templatesSrcKotlinDir = srcAppKotlinDir / "templates"
val appBuildPath = "app/build.gradle.kts".toPath()
val lib1BuildPath = "lib1/build.gradle.kts".toPath()
val libUiSamplesBuildPath = "lib-ui-samples/build.gradle.kts".toPath()

tasks.registerAllThatGroupFun(
    "inject",
    ::checkPlaygroundsBuildTemplates,
    ::injectAndroAppBuildTemplate,
    ::injectAndroLib1BuildTemplate,
    ::injectAndroLibUiSamplesBuildTemplate,
)

fun checkPlaygroundsBuildTemplates() = checkAndroBuildTemplates(appBuildPath, lib1BuildPath, libUiSamplesBuildPath)
fun injectAndroAppBuildTemplate() = injectAndroAppBuildTemplate(appBuildPath)
fun injectAndroLib1BuildTemplate() = injectAndroLibBuildTemplate(lib1BuildPath)
fun injectAndroLibUiSamplesBuildTemplate() = injectAndroLibBuildTemplate(libUiSamplesBuildPath)

// TODO NOW: test sourceFun DSL
sourceFun {
    val srcTests = androidxSupportDir / "compose/foundation/foundation/src/androidAndroidTest/kotlin/androidx/compose/foundation"
    val srcAnnot = androidxSupportDir / "annotation/annotation-sampled/src/main/java/androidx/annotation"
    val srcJava = androidxSupportDir / "compose/ui/ui-android-stubs/src/main/java/android/view"
    val srcTestUtilsCommon = androidxSupportDir / "compose/test-utils/src/commonMain/kotlin/androidx/compose/testutils"
    val srcTestUtilsAndro = androidxSupportDir / "compose/test-utils/src/androidMain/kotlin/androidx/compose/testutils"
    val srcSamplesUi = androidxSupportDir / "compose/ui/ui/samples/src/main/java/androidx/compose/ui/samples"
    val srcSamplesUiGraphics = androidxSupportDir / "compose/ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples"
    val srcSamplesUiAnimationCore = androidxSupportDir / "compose/animation/animation-core/samples/src/main/java/androidx/compose/animation/core/samples"
    val srcSamplesUiAnimation = androidxSupportDir / "compose/animation/animation/samples/src/main/java/androidx/compose/animation/samples"
    grp = "steal"
    def("stealComposeTests", srcTests, stolenAndroTestsDir / "foundation-tests") { if ("CanvasTest" in name || "Foundation" in name) it else null }
    def("stealComposeAnnotations", srcAnnot, stolenSamplesKotlinDir / "androidx-annotation") { it }
    def("stealComposeSourcesJava", srcJava, stolenSrcJavaDir / "android/view") { it }
    def("stealComposeSourcesTestUtilsCommon", srcTestUtilsCommon, stolenSrcKotlinDir / "compose-testutils") { it }
    def("stealComposeSourcesTestUtilsAndro", srcTestUtilsAndro, stolenSrcKotlinDir / "compose-testutils") { if ("Screenshot" in name) null else it }

    def("stealComposeSamplesUi", srcSamplesUi, stolenSamplesKotlinDir / "samples-ui") { it }
    def("stealComposeSamplesUiGraphics", srcSamplesUiGraphics, stolenSamplesKotlinDir / "samples-ui-graphics") { it }
    def("stealComposeSamplesAnimationCore", srcSamplesUiAnimationCore, stolenSamplesKotlinDir / "samples-animation-core") { it }
    def("stealComposeSamplesAnimation", srcSamplesUiAnimation, stolenSamplesKotlinDir / "samples-animation") { it }
}

val stealComposeSources by tasks.registering {
    group = "steal"
    dependsOn("stealComposeSourcesJava")
    dependsOn("stealComposeSourcesTestUtilsCommon")
    dependsOn("stealComposeSourcesTestUtilsAndro")
}

val stealComposeAll by tasks.registering {
    group = "steal"
    dependsOn("stealComposeTests")
    dependsOn("stealComposeAnnotations")
    dependsOn("stealComposeSources")

    // TODO NOW: check if input output match will cause process to automatically depend on steal..Samples..
    dependsOn("processStolenSamples")
}

val processStolenSamples by tasks.registering(SourceFunTask::class) {
    group = "steal"
    addSource(stolenSamplesKotlinDir)
    setOutput(templatesSrcKotlinDir)
    setTaskAction { source: FileTree, output: Directory ->
        val samples = mutableListOf<Pair<String, Path?>>() // funName to filePath
        source.visit {
            if (!isDirectory) {
                val samplePath = file.toOkioPath()
                val sampleContent = SYSTEM.readUtf8(samplePath)
                val pkg = sampleContent.ktFindPackageName()
                samples += sampleContent.findSampledComposableFunNames().map { "$pkg.$it" to samplePath }
            }
        }
        val interpolations = mapOf(
//            stolenSrcKotlinDir.toString() to "stolenSrcKotlinDir",
//            templatesSrcKotlinDir.toString() to "templatesSrcKotlinDir",
            srcLibUiSamplesKotlinDir.toString() to "samplesDir",
        )
        processTemplates(templatesSrcKotlinDir, samples, interpolations)
    }
}

fun String.ktFindPackageName() = urePackageLine().compile().find(this)!!["ktPackageName"]

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
    val wholeRegion = "// region $regionName\n$newGenerationArea// endregion $regionName\n"
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
        .replace(ureLineWithEndingComment(ir("REMOVE")).compile(), "")
        .replace(ureLineWithEndingComment(ir("REPLACE")).compile(),
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


val ureParamsNotNested = ure { // I assume no internal expressions with parenthesis
    1 of ch("\\(")
    0..MAX of oneCharNotOf("(")
    1 of ch("\\)")
}

val ureAnnotations = ure {
    1..MAX of { // single annotation
        1 of ch("@")
        1 of ureIdent(AZ)
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
    1 of ure { 1 of ureIdent(AZ) }.withName("funName")
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
