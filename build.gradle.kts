@file:Suppress("UNUSED_VARIABLE")

import okio.FileSystem.Companion.SYSTEM
import okio.Path.Companion.toPath
import okio.Path
import okio.Path.Companion.toOkioPath
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.ure.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.sourcefun.*
import pl.mareklangiewicz.io.*

plugins {
    kotlin("android") apply false
    id("io.github.gradle-nexus.publish-plugin") version vers.nexusPublishGradlePlugin
    id("pl.mareklangiewicz.sourcefun")
}

defaultGroupAndVerAndDescription(libs.MyStolenPlaygrounds)

defaultSonatypeOssStuffFromSystemEnvs()

val rootBuildPath = rootProjectPath / "build.gradle.kts"

val playgroundsAppPath = rootProjectPath / "playgrounds-app"
val playgroundsBasicPath = rootProjectPath / "playgrounds-basic"
val playgroundsUiSamplesPath = rootProjectPath / "playgrounds-ui-samples"
val playgroundsMaterial3Path = rootProjectPath / "playgrounds-material3"

val playgroundsAppBuildPath = playgroundsAppPath / "build.gradle.kts"
val playgroundsBasicBuildPath = playgroundsBasicPath / "build.gradle.kts"
val playgroundsUiSamplesBuildPath = playgroundsUiSamplesPath / "build.gradle.kts"
val playgroundsMaterial3BuildPath = playgroundsMaterial3Path / "build.gradle.kts"

val rootAndroidxPath = "/home/marek/code/android/androidx-main".toPath()
// val rootAndroidxPath = "/home/marek/code/kotlin/compose-jb/compose".toPath()
val androidxSupportPath = rootAndroidxPath / "frameworks/support"

val srcAppKotlinPath = playgroundsAppPath / "src/main/kotlin"
val srcBasicKotlinPath = playgroundsBasicPath / "src/main/kotlin"
val srcBasicJavaPath = playgroundsBasicPath / "src/main/java"
val srcUiSamplesKotlinPath = playgroundsUiSamplesPath / "src/main/kotlin"
val srcMaterial3KotlinPath = playgroundsMaterial3Path / "src/main/kotlin"

val stolenBasicKotlinPath = srcBasicKotlinPath / "stolen"
val stolenBasicJavaPath = srcBasicJavaPath // java files have to be in directories same as packages :(
val stolenUiSamplesKotlinPath = srcUiSamplesKotlinPath / "stolen"
val stolenMaterial3KotlinPath = srcMaterial3KotlinPath / "stolen"
val stolenBasicAndroTestsPath = playgroundsBasicPath / "src/androidTest/kotlin/stolen"
val templatesAppSrcKotlinPath = srcAppKotlinPath / "templates"

tasks.registerAllThatGroupFun("inject", ::checkBuildTemplates, ::injectBuildTemplates)

fun checkBuildTemplates() = checkAllKnownRegionsInProject()

fun injectBuildTemplates() = injectAllKnownRegionsInProject()

fun String.containsOneOf(vararg substrings: String) = substrings.any { it in this }

// TODO NOW: test sourceFun DSL
sourceFun {

    grp = "steal"

    val stealComposeFoundationTests by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/foundation/foundation/src/androidAndroidTest/kotlin/androidx/compose/foundation"
        out = stolenBasicAndroTestsPath / "foundation-tests"
        setTransformFun { if (name.containsOneOf("CanvasTest", "Foundation", "TestActivity")) it else null }
    }
    val stealComposeFoundationLayoutTests by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/foundation/foundation-layout/src/androidAndroidTest/kotlin/androidx/compose/foundation/layout"
        out = stolenBasicAndroTestsPath / "foundation-layout-tests"
        setTransformFun {
            val interesting = name.containsOneOf("BoxTest", "LayoutTest", "IntrinsicTest", "SizeTest", "PaddingTest", "OffsetTest")
            if (interesting && "Window" !in name) it else null
        }
    }
    val stealComposeAnnotations by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "annotation/annotation-sampled/src/main/java/androidx/annotation"
        out = stolenUiSamplesKotlinPath / "androidx-annotation"
        setTransformFun { it }
    }
    val stealComposeSourcesJava by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/ui/ui-android-stubs/src/main/java/android/view"
        out = stolenBasicJavaPath / "android/view"
        setTransformFun { it }
    }
    val stealComposeSourcesTestUtilsCommon by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/test-utils/src/commonMain/kotlin/androidx/compose/testutils"
        out = stolenBasicKotlinPath / "compose-testutils"
        setTransformFun { it }
    }
    val stealComposeSourcesTestUtilsAndro by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/test-utils/src/androidMain/kotlin/androidx/compose/testutils"
        out = stolenBasicKotlinPath / "compose-testutils"
        setTransformFun { if ("Screenshot" in name) null else it }
    }
    val stealComposeSamplesUi by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/ui/ui/samples/src/main/java/androidx/compose/ui/samples"
        out = stolenUiSamplesKotlinPath / "samples-ui"
        setTransformFun { it }
    }
    val stealComposeSamplesUiGraphics by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples"
        out = stolenUiSamplesKotlinPath / "samples-ui-graphics"
        setTransformFun { it }
    }
    val stealComposeSamplesAnimationCore by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/animation/animation-core/samples/src/main/java/androidx/compose/animation/core/samples"
        out = stolenUiSamplesKotlinPath / "samples-animation-core"
        setTransformFun { it }
    }
    val stealComposeSamplesAnimation by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/animation/animation/samples/src/main/java/androidx/compose/animation/samples"
        out = stolenUiSamplesKotlinPath / "samples-animation"
        setTransformFun { it }
    }
    val m3 = androidxSupportPath / "compose/material3/material3"
    val stealComposeMaterial3Samples by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = m3 / "samples/src/main/java/androidx/compose/material3/samples"
        out = stolenMaterial3KotlinPath / "samples"
        setTransformFun { it }
    }
    val stealComposeMaterial3Catalog by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = m3 / "integration-tests/material3-catalog/src/main/java/androidx/compose/material3/catalog"
        out = stolenMaterial3KotlinPath / "catalog"
        setTransformFun { it }
    }
    val stealComposeMaterial3Demos by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = m3 / "integration-tests/material3-demos/src/main/java/androidx/compose/material3/demos"
        out = stolenMaterial3KotlinPath / "demos"
        setTransformFun { it }
    }
    val stealComposeCommonDemos by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = androidxSupportPath / "compose/integration-tests/demos/common/src/main/java/androidx/compose/integration/demos/common"
        out = stolenMaterial3KotlinPath / "demos-common"
        setTransformFun { it }
    }
    val stealComposeSourcesAll by reg { dependsOn(
        stealComposeSourcesJava,
        stealComposeSourcesTestUtilsCommon,
        stealComposeSourcesTestUtilsAndro,
    ) }
    val stealComposeSamplesAll by reg { dependsOn(
        stealComposeSamplesUi,
        stealComposeSamplesUiGraphics,
        stealComposeSamplesAnimationCore,
        stealComposeSamplesAnimation,
    ) }
    val stealComposeMaterial3All by reg { dependsOn(
        stealComposeMaterial3Samples,
        stealComposeMaterial3Catalog,
        stealComposeMaterial3Demos,
        stealComposeCommonDemos,
    ) }
    val stealAll by reg { dependsOn(
        stealComposeFoundationTests,
        stealComposeFoundationLayoutTests,
        stealComposeAnnotations,
        stealComposeSourcesAll,
        stealComposeSamplesAll,
        stealComposeMaterial3All,
    ) }

    val processStolenUiSamples by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        dependsOn(stealComposeSamplesAll)
        src = stolenUiSamplesKotlinPath
        out = templatesAppSrcKotlinPath
        setTaskAction { srcTree: FileTree, outDir: Directory ->
            val samples = mutableListOf<Pair<String, Path?>>() // funName to filePath
            srcTree.visit {
                if (isDirectory) return@visit
                val samplePath = file.toOkioPath()
                val sampleContent = SYSTEM.readUtf8(samplePath)
                val pkg = sampleContent.ktFindPackageName()
                samples += sampleContent.findSampledComposableFunNames().map { "$pkg.$it" to samplePath }
            }
            processComposeTemplates(outDir, samples)
        }
    }
}

fun String.ktFindPackageName() = urePackageLine().compile().find(this)!!["ktPackageName"]

fun processComposeTemplates(
    templatesDir: Directory,
    samples: Collection<Pair<String, Path?>>,
) = processTemplates(
    templatesPath = templatesDir.asFile.toOkioPath(),
    samples = samples,
    interpolations = mapOf(
//            stolenSrcKotlinDir.toString() to "stolenSrcKotlinDir",
//            templatesSrcKotlinDir.toString() to "templatesSrcKotlinDir",
    srcUiSamplesKotlinPath.toString() to "samplesDir",
))

fun processTemplates(
    templatesPath: Path,
    samples: Collection<Pair<String, Path?>>,
    interpolations: Map<String, String>
) = SYSTEM.processEachFile(templatesPath, templatesPath) { _, _, templateFileContent ->

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

// region [Root Build Template]

/**
 * System.getenv() should contain six env variables with given prefix, like:
 * * MYKOTLIBS_signing_keyId
 * * MYKOTLIBS_signing_password
 * * MYKOTLIBS_signing_key
 * * MYKOTLIBS_ossrhUsername
 * * MYKOTLIBS_ossrhPassword
 * * MYKOTLIBS_sonatypeStagingProfileId
 * * First three of these used in fun pl.mareklangiewicz.defaults.defaultSigning
 * * See deps.kt/template-mpp/template-mpp-lib/build.gradle.kts
 */
fun Project.defaultSonatypeOssStuffFromSystemEnvs(envKeyMatchPrefix: String = "MYKOTLIBS_") {
    ext.addAllFromSystemEnvs(envKeyMatchPrefix)
    defaultSonatypeOssNexusPublishing()
}

fun Project.defaultSonatypeOssNexusPublishing(
    sonatypeStagingProfileId: String = rootExt("sonatypeStagingProfileId"),
    ossrhUsername: String = rootExt("ossrhUsername"),
    ossrhPassword: String = rootExt("ossrhPassword"),
) = nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            stagingProfileId put sonatypeStagingProfileId
            username put ossrhUsername
            password put ossrhPassword
            nexusUrl put uri(repos.sonatypeOssNexus)
            snapshotRepositoryUrl put uri(repos.sonatypeOssSnapshots)
        }
    }
}

// endregion [Root Build Template]